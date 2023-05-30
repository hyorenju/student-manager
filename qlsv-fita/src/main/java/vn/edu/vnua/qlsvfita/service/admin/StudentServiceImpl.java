package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.model.dto.*;
import vn.edu.vnua.qlsvfita.model.entity.*;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.student.*;
import vn.edu.vnua.qlsvfita.util.MyUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final PasswordEncoder encoder;
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;
    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final String[] HEADERS = {"Mã sinh viên", "Họ tên", "Mã khóa", "Mã ngành", "Mã lớp", "Ngày sinh", "Giới tính", "Trạng thái", "Thời gian", "Số điện thoại", "Email", "Quê quán", "Nơi ở hiện tại", "Tên bố", "Sđt bố", "Tên mẹ", "Sđt mẹ", "Diện cảnh cáo"};

    @Override
    public List<StudentListDTOTest> getStudenList() {
        List<Student> students = studentRepository.findAll();
//        List<StudentListDTO> studentListDTOS = new ArrayList<>();
//        for (Student student :
//                students) {
//            StudentListDTO studentListDTO = modelMapper.map(student, StudentListDTO.class);
//            int date = student.getDob().getDate();
//            int month = student.getDob().getMonth() + 1;
//            int year = student.getDob().getYear() + 1900;
//            String stringDob = date + "/" + month + "/" + year;
//            studentListDTO.setDob(stringDob);
//
//            if (student.getStatusDate() != null) {
//                date = student.getStatusDate().getDate();
//                month = student.getStatusDate().getMonth() + 1;
//                year = student.getStatusDate().getYear() + 1900;
//                String stringStatusDate = date + "/" + month + "/" + year;
//                studentListDTO.setDob(stringStatusDate);
//                studentListDTOS.add(studentListDTO);
//            }
//        }
        return students.stream().map(
                student -> modelMapper.map(student, StudentListDTOTest.class)
        ).collect(Collectors.toList());
    }

    @Override
    public Page<Student> filterStudentList(GetStudentListRequest request) {
//        if (request.getStudentId() != null && !request.getStudentId().equals("")) {
//            if (!studentRepository.existsById(request.getStudentId())) {
//                throw new RuntimeException("Mã sinh viên không tồn tại trong hệ thống");
//            }
//        }
        Specification<Student> specification = CustomStudentListRepository.filterStudentList(
                request.getFilter().getCourseId(),
                request.getFilter().getMajorId(),
                request.getFilter().getClassId(),
                request.getFilter().getStatus(),
                request.getStudentId()
        );

        if (request.getFilter().getSort() != null) {
            if (request.getFilter().getSort().isName()) {
                return studentRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("name").ascending()));
            }
        }
        return studentRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Student getStudentById(String id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Mã sinh viên " + id + " không tồn tại trong hệ thống");
        }
        return studentRepository.getStudentById(id);
    }

    @Override
    public StudentAccumulationDTO getAccumulation(String id) {
//        List<StudentTerm> studentTerms = pointRepository.findAllByStudentId(id);
//        StudentAccumulationDTO studentAccumulationDTO = new StudentAccumulationDTO();
//        studentAccumulationDTO.setTotalCredits(majorRepository.getById(studentRepository.getStudentById(id).getMajor().getId()).getTotalCredits());
//
//        if(studentTerms.size()==0){
//            studentAccumulationDTO.setCreditsAccumulated(0);
//            studentAccumulationDTO.setScoreAccumulated10((float) 0);
//            studentAccumulationDTO.setScoreAccumulated4((float) 0);
//            return studentAccumulationDTO;
//        }
//        int i = studentTerms.size() - 1;
//        return modelMapper.map(studentTerms.get(i), StudentAccumulationDTO.class);

        List<StudentTerm> studentTerms = pointRepository.findAllByStudentId(id);

        StudentAccumulationDTO studentAccumulationDTO = new StudentAccumulationDTO();
        studentAccumulationDTO.setTotalCredits(majorRepository.getById(studentRepository.getStudentById(id).getMajor().getId()).getTotalCredits());
        if (studentTerms.size() == 0) {
            studentAccumulationDTO.setCreditsAccumulated(0);
            studentAccumulationDTO.setScoreAccumulated10((float) 0);
            studentAccumulationDTO.setScoreAccumulated4((float) 0);
            return studentAccumulationDTO;
        }
        int max = Integer.parseInt(studentTerms.get(0).getTermId());
        for (StudentTerm point :
                studentTerms) {
            if (Integer.parseInt(point.getTermId()) > max) {
                max = Integer.parseInt(point.getTermId());
            }
        }
        studentAccumulationDTO.setCreditsAccumulated(pointRepository.getByStudentIdAndTermId(id, Integer.toString(max)).getCreditsAccumulated());
        studentAccumulationDTO.setScoreAccumulated10(pointRepository.getByStudentIdAndTermId(id, Integer.toString(max)).getScoreAccumulated10());
        studentAccumulationDTO.setScoreAccumulated4(pointRepository.getByStudentIdAndTermId(id, Integer.toString(max)).getScoreAccumulated4());
        return studentAccumulationDTO;
    }

    @Override
    public StudentDetailDTO createStudent(CreateStudentRequest request) throws ParseException {
        if (studentRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã sinh viên đã tồn tại trong hệ thống");
        }
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }
        if (!classRepository.existsById(request.getClasses().getId())) {
            throw new RuntimeException("Mã lớp không tồn tại trong hệ thống");
        }
        if (!courseRepository.existsById(request.getCourse().getId())) {
            throw new RuntimeException("Mã khóa không tồn tại trong hệ thống");
        }
        if (!majorRepository.existsById(request.getMajor().getId())) {
            throw new RuntimeException("Mã Chuyên ngành không tồn tại trong hệ thống");
        }
        Student student = Student.builder()
                .id(request.getId())
                .name(request.getName())
                .course(Course.builder().id(request.getCourse().getId()).build())
                .major(Major.builder().id(request.getMajor().getId()).build())
                .classes(Class.builder().id(request.getClasses().getId()).build())
                .dob(MyUtils.convertTimestampFromString(request.getDob()))
                .gender(request.getGender())
//                .status(request.getStatus())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .homeTown(request.getHomeTown())
                .residence(request.getResidence())
                .fatherName(request.getFatherName())
                .fatherPhoneNumber(request.getFatherPhoneNumber())
                .motherName(request.getMotherName())
                .motherPhoneNumber(request.getMotherPhoneNumber())
                .role(Role.builder().id("STUDENT").build())
                .warning(request.getWarning())
                .build();

        if (Objects.equals(request.getStatus(), "stillStudying")) {
            student.setStatus("Còn đi học");
        } else if (Objects.equals(request.getStatus(), "graduated")) {
            student.setStatus("Đã tốt nghiệp");
        } else if (Objects.equals(request.getStatus(), "dropped")) {
            student.setStatus("Đã bỏ học");
        } else if (Objects.equals(request.getStatus(), "forcedOut")) {
            student.setStatus("Bị buộc thôi học");
        }

        if (request.getStatusDate() != null) {
            student.setStatusDate(MyUtils.convertTimestampFromString(request.getStatusDate()));
        }

        if (request.getPassword() == null) {
            student.setPassword(encoder.encode(request.getDob()));
        } else {
            student.setPassword(encoder.encode(request.getPassword()));
        }

        student = studentRepository.saveAndFlush(student);
        return modelMapper.map(student, StudentDetailDTO.class);
    }

    @Override
    public StudentDetailDTO updateStudent(UpdateStudentRequest request, String id) throws ParseException {
        if (!studentRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã sinh viên " + request.getId() + " không tồn tại trong hệ thống");
        }
        Student student = studentRepository.getStudentById(request.getId());
        student.setName(request.getName());
        student.setCourse(Course.builder().id(request.getCourse().getId()).build());
        student.setMajor(Major.builder().id(request.getMajor().getId()).build());
        student.setClasses(Class.builder().id(request.getClasses().getId()).build());
        student.setDob(MyUtils.convertTimestampFromString(request.getDob()));
        student.setGender(request.getGender());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setEmail(request.getEmail());
        student.setHomeTown(request.getHomeTown());
        student.setResidence(request.getResidence());
        student.setFatherName(request.getFatherName());
        student.setFatherPhoneNumber(request.getFatherPhoneNumber());
        student.setMotherName(request.getMotherName());
        student.setMotherPhoneNumber(request.getMotherPhoneNumber());
        student.setWarning(request.getWarning());


        if (Objects.equals(request.getStatus(), "stillStudying")) {
            student.setStatus("Còn đi học");
        } else if (Objects.equals(request.getStatus(), "graduated")) {
            student.setStatus("Đã tốt nghiệp");
        } else if (Objects.equals(request.getStatus(), "dropped")) {
            student.setStatus("Đã bỏ học");
        } else if (Objects.equals(request.getStatus(), "forcedOut")) {
            student.setStatus("Bị buộc thôi học");
        }

        if (request.getPassword() == null) {
            student.setPassword(encoder.encode(student.getPassword()));
        } else {
            student.setPassword(encoder.encode(request.getPassword()));
        }

        if (request.getStatusDate() != null) {
            student.setStatusDate(MyUtils.convertTimestampFromString(request.getStatusDate()));
        }

        student = studentRepository.saveAndFlush(student);
        return modelMapper.map(student, StudentDetailDTO.class);
    }

    @Override
    public void deleteStudentById(String id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Sinh viên " + id + " không tồn tại trong CSDL");
        }
        studentRepository.deleteById(id);
    }


    @Override
    public void deleteAllStudentById(DeleteStudentRequest request) {
        for (String id :
                request.getIds()) {
            if (!studentRepository.existsById(id)) {
                throw new RuntimeException("Có ít nhất 1 id không tồn tại trong CSDL");
            }
        }
        studentRepository.deleteAllByIdInBatch(request.getIds());
    }

    @Override
    public List<StudentExportDTO> exportToExcel(ExportStudentListRequest request) {
//        if (request.getStudentId() != null && !request.getStudentId().equals("")) {
//            if (!studentRepository.existsById(request.getStudentId())) {
//                throw new RuntimeException("Mã sinh viên không tồn tại trong hệ thống");
//            }
//        }
        Specification<Student> specification = CustomStudentListRepository.filterStudentList(
                request.getFilter().getCourseId(),
                request.getFilter().getMajorId(),
                request.getFilter().getClassId(),
                request.getFilter().getStatus(),
                request.getStudentId()
        );

        if (request.getFilter().getSort() != null) {
            if (request.getFilter().getSort().isName()) {
                List<Student> students = studentRepository.findAllByOrderByNameAsc();
                List<StudentExportDTO> studentExports = new ArrayList<>();
                for (Student student : students) {
                    StudentExportDTO anotherStudent = modelMapper.map(studentRepository.getStudentById(student.getId()), StudentExportDTO.class);
                    anotherStudent.setMajorId(student.getMajor().getId());
                    anotherStudent.setCourseId(student.getCourse().getId());
                    anotherStudent.setClassId(student.getClasses().getId());
                    studentExports.add(anotherStudent);
                }
                return studentExports;
            }
        }

        List<Student> students = studentRepository.findAll(specification);
        List<StudentExportDTO> studentExports = new ArrayList<>();
        for (Student student : students) {
            StudentExportDTO anotherStudent = modelMapper.map(studentRepository.getStudentById(student.getId()), StudentExportDTO.class);
            anotherStudent.setMajorId(student.getMajor().getId());
            anotherStudent.setCourseId(student.getCourse().getId());
            anotherStudent.setClassId(student.getClasses().getId());
            studentExports.add(anotherStudent);
        }
        return studentExports;
    }

    @Override
    public void importToDatabase(MultipartFile file) throws IOException, ParseException {
        // Đọc dữ liệu từ tệp Excel
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rowIterator = sheet.iterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            Cell cell1 = row.getCell(0);
//            Cell cell2 = row.getCell(1);
//            Cell cell3 = row.getCell(2);
//            Cell cell4 = row.getCell(3);
//            Cell cell5 = row.getCell(4);
//
//            if (studentRepository.existsById(String.valueOf((int) row.getCell(0).getNumericCellValue()))) {
//                throw new RuntimeException("Có ít nhất 1 mã sinh viên đã tồn tại trong hệ thống");
//            }
//
//            Student student = new Student();
//            student.setId(cell1.getStringCellValue());
//            String name = cell2.getStringCellValue() + " " + cell3.getStringCellValue();
//            student.setName(name);
//            student.setDob(MyUtils.convertTimestampFromString(cell4.getStringCellValue()));
//            student.setHomeTown(cell5.getStringCellValue());
//
//            studentRepository.saveAndFlush(student);
//        }
//        workbook.close();

        List<Student> students = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue; // Skip header row
            }

            if (studentRepository.existsById(String.valueOf((int) row.getCell(0).getNumericCellValue()))) {
                throw new RuntimeException("Có ít nhất 1 mã sinh viên đã tồn tại trong hệ thống");
            }

            Student student = new Student();
            student.setId(String.valueOf((int) row.getCell(0).getNumericCellValue()));
            String name = row.getCell(1).getStringCellValue() + " " + row.getCell(2).getStringCellValue();
            student.setName(name);
            student.setDob(MyUtils.convertTimestampFromString(row.getCell(3).getStringCellValue()));
            student.setHomeTown(row.getCell(4).getStringCellValue());
            student.setPassword(encoder.encode(row.getCell(3).getStringCellValue()));
            student.setStatus("Còn đi học");
            student.setWarning("Không bị cảnh cáo");

            students.add(student);
        }
        workbook.close();

        // Lưu sản phẩm vào CSDL
        studentRepository.saveAll(students);
    }

//    @Override
//    public void importFileToDatabase(MultipartFile file) throws IOException, ParseException {
//        // Đọc dữ liệu sản phẩm từ tệp Excel
//        List<Student> students = new ArrayList<>();
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rowIterator = sheet.iterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            if (row.getRowNum() == 0) {
//                continue; // Skip header row
//            }
//            Student student = new Student();
//            student.setId(String.valueOf((int) row.getCell(0).getNumericCellValue()));
//            student.setGender(row.getCell(4).getStringCellValue());
//            student.setClasses(Class.builder().id(row.getCell(5).getStringCellValue()).build());
//            student.setName(row.getCell(13).getStringCellValue());
//            student.setDob(MyUtils.convertTimestampFromString(row.getCell(12).getStringCellValue()));
////            student.setPrice(new BigDecimal(row.getCell(1).getNumericCellValue()));
////            student.setQuantity((int) row.getCell(2).getNumericCellValue());
//            students.add(student);
//        }
//        workbook.close();
//
//        // Lưu sản phẩm vào CSDL
//        studentRepository.saveAll(students);
//    }


//    @Override
//    public void importToExcel() throws IOException, ParseException {
//        List<StudentList> studentLists = new ArrayList<>();
//
//        FileInputStream file = new FileInputStream(new File("D:\\Mine\\InternshipProject\\data\\from-faculty\\edited\\dssv-import.xlsx"));
//
//        //Create Workbook instance holding reference to .xlsx file
//        XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//        //Get first/desired sheet from the workbook
//        XSSFSheet sheet = workbook.getSheetAt(0);
//
//        //Iterate through each rows one by one
//        Iterator<Row> rowIterator = sheet.iterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            //For each row, iterate through all the columns
//            Iterator<Cell> cellIterator = row.cellIterator();
//
//            StudentList studentList = new StudentList();
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
//                //Check the cell type and format accordingly
//                if(cell.getCellType() == CellType.NUMERIC) {
//                    break;
//                } else if(cell.getCellType() == CellType.STRING) {
//                    //Set the data to the corresponding fields of ExcelData object
//                    if (cell.getColumnIndex() == 0) {
//                        studentList.setColumn1(cell.getStringCellValue());
//                    } else if (cell.getColumnIndex() == 1) {
//                        studentList.setColumn2(cell.getStringCellValue());
//                    } else if (cell.getColumnIndex() == 2) {
//                        studentList.setColumn3(cell.getStringCellValue());
//                    } else if (cell.getColumnIndex() == 3) {
//                        studentList.setColumn4(cell.getStringCellValue());
//                    } else if (cell.getColumnIndex() == 4) {
//                        studentList.setColumn5(cell.getStringCellValue());
//                    }
//                }
//            }
//
//            studentLists.add(studentList);
//        }
//
//
//        for (StudentList studentList : studentLists) {
//            Student student = new Student();
//            student.setId(studentList.getColumn1());
//            String studentName = studentList.getColumn2() + studentList.getColumn3();
//            student.setName(studentName);
//            student.setDob(MyUtils.convertTimestampFromString(studentList.getColumn4()));
//
//            studentRepository.save(student);
//        }
//    }
}
