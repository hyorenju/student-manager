package vn.edu.vnua.fita.student.service.admin.management;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.common.ThreadsNumConstant;
import vn.edu.vnua.fita.student.entity.authorization.Role;
import vn.edu.vnua.fita.student.entity.dto.StudentExportDTO;
import vn.edu.vnua.fita.student.entity.manager.trash.TrashStudent;
import vn.edu.vnua.fita.student.entity.table.Class;
import vn.edu.vnua.fita.student.repository.customrepo.CustomStudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.request.admin.student.*;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;
import vn.edu.vnua.fita.student.service.iservice.IStudentService;
import vn.edu.vnua.fita.student.util.MyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.table.*;
import com.google.cloud.storage.Blob;


import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentManager implements IStudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final TrashStudentRepository trashStudentRepository;
    private final PasswordEncoder encoder;
    private final FirebaseService firebaseService;
    private final String studentHadExisted = "Sinh viên %s đã tồn tại trong hệ thống";
    private final String studentNotFound = "Không tìm thấy sinh viên %s";
    private final String courseNotFound = "Không tìm thấy khoá %s";
    private final String classNotFound = "Không tìm thấy lớp %s";
    private final String majorNotFound = "Không tìm thấy chuyên ngành %s";

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public Page<Student> getStudentList(GetStudentListRequest request) {
        Specification<Student> specification = CustomStudentRepository.filterStudentList(
                request.getFilter().getCourseId(),
                request.getFilter().getMajorId(),
                request.getFilter().getClassId(),
                request.getStudentId()
        );
        return studentRepository.findAll(
                specification,
                PageRequest.of(request.getPage() - 1, request.getSize())
        );
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student createStudent(CreateStudentRequest request) {
        if (studentRepository.existsById(request.getId())) {
            throw new RuntimeException(String.format(studentHadExisted, request.getId()));
        }
        Course course = courseRepository.findById(request.getCourse().getId()).orElseThrow(() -> new RuntimeException(String.format(courseNotFound, request.getCourse().getId())));
        Class aClass = classRepository.findById(request.getClasses().getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getClasses().getId())));
        Major major = majorRepository.findById(request.getMajor().getId()).orElseThrow(() -> new RuntimeException(String.format(majorNotFound, request.getMajor().getId())));

        Student student = Student.builder()
                .id(request.getId())
                .surname(request.getSurname())
                .lastName(request.getLastName())
                .course(course)
                .major(major)
                .classes(aClass)
                .dob(MyUtils.convertTimestampFromString(request.getDob()))
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .homeTown(request.getHomeTown())
                .residence(request.getResidence())
                .fatherName(request.getFatherName())
                .fatherPhoneNumber(request.getFatherPhoneNumber())
                .motherName(request.getMotherName())
                .motherPhoneNumber(request.getMotherPhoneNumber())
                .isDeleted(false)
                .role(Role.builder().id(RoleConstant.STUDENT).build())
                .build();
        if (StringUtils.hasText(request.getPassword())) {
            student.setPassword(encoder.encode(request.getPassword()));
        } else {
            student.setPassword(encoder.encode(MyUtils.formatDobToPassword(request.getDob())));

        }

        studentRepository.saveAndFlush(student);
        createStudentStatus(student);

        return student;
    }

    @Override
    public Student updateStudent(UpdateStudentRequest request){
        Student student = studentRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, request.getId())));
        Course course = courseRepository.findById(request.getCourse().getId()).orElseThrow(() -> new RuntimeException(String.format(courseNotFound, request.getCourse().getId())));
        Class aClass = classRepository.findById(request.getClasses().getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getClasses().getId())));
        Major major = majorRepository.findById(request.getMajor().getId()).orElseThrow(() -> new RuntimeException(String.format(majorNotFound, request.getMajor().getId())));

        student.setSurname(request.getSurname());
        student.setLastName(request.getLastName());
        student.setCourse(course);
        student.setMajor(major);
        student.setClasses(aClass);
        student.setDob(MyUtils.convertTimestampFromString(request.getDob()));
        student.setGender(request.getGender());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setHomeTown(request.getHomeTown());
        student.setResidence(request.getResidence());
        student.setFatherName(request.getFatherName());
        student.setFatherPhoneNumber(request.getFatherPhoneNumber());
        student.setMotherName(request.getMotherName());
        student.setMotherPhoneNumber(request.getMotherPhoneNumber());
        if (StringUtils.hasText(request.getPassword())) {
            student.setPassword(encoder.encode(request.getPassword()));
        }
        studentRepository.saveAndFlush(student);

        return student;
    }

    @Override
    public TrashStudent deleteStudent(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));
        student.setIsDeleted(true);
        TrashStudent trashStudent = moveToTrash(student);
        studentRepository.saveAndFlush(student);
        return trashStudent;
    }

    @Override
    public List<TrashStudent> deleteManyStudent(DeleteStudentRequest request) {
        List<Student> students = checkIds(request.getIds());
        List<TrashStudent> trashStudents = new ArrayList<>();
        students.forEach(student -> {
            student.setIsDeleted(true);
            studentRepository.saveAndFlush(student);
            trashStudents.add(moveToTrash(student));
        });
        return trashStudents;
    }

    @Override
    public TrashStudent restoreStudent(Long id) {
        TrashStudent trashStudent = trashStudentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác muốn khôi phục"));
        Student student = trashStudent.getStudent();
        student.setIsDeleted(false);
        restoreFromTrash(student);
        studentRepository.saveAndFlush(student);
        return trashStudent;
    }

    @Override
    public List<TrashStudent> restoreManyStudent(RestoreStudentRequest request) {
        List<TrashStudent> trashStudents = trashStudentRepository.findAllById(request.getIds());
        trashStudents.forEach(trashStudent -> {
            Student student = trashStudent.getStudent();
            student.setIsDeleted(false);
            restoreFromTrash(student);
            studentRepository.saveAndFlush(student);
        });
        return trashStudents;
    }

    @Override
    public Page<TrashStudent> getTrashStudentList(GetTrashStudentRequest request) {
        return trashStudentRepository.findAll(PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending()));
    }

    @Override
    public Student updateAvatar(MultipartFile file, String id) throws IOException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));

        Blob blob = firebaseService.uploadImage(file, bucketName);

        if (StringUtils.hasText(student.getAvatar())) {
            String[] strings = student.getAvatar().split("[/?]");
            blob.getStorage().delete(bucketName, strings[9]);
        }

        student.setAvatar(blob.getMediaLink());

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public List<StudentExportDTO> exportToExcel(ExportStudentListRequest request) {
        Specification<Student> specification = CustomStudentRepository.filterStudentList(
                request.getFilter().getCourseId(),
                request.getFilter().getMajorId(),
                request.getFilter().getClassId(),
                request.getStudentId()
        );
        List<Student> students = studentRepository.findAll(specification);

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dssv");

            // Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mã sinh viên");
            headerRow.createCell(1).setCellValue("Họ đệm");
            headerRow.createCell(2).setCellValue("Tên");
            headerRow.createCell(3).setCellValue("Khoá");
            headerRow.createCell(4).setCellValue("Chuyên ngành");
            headerRow.createCell(5).setCellValue("Lớp");
            headerRow.createCell(6).setCellValue("Ngày sinh");
            headerRow.createCell(7).setCellValue("Giới tính");
            headerRow.createCell(8).setCellValue("Số điện thoại");
            headerRow.createCell(9).setCellValue("Email");
            headerRow.createCell(10).setCellValue("Quê quán");
            headerRow.createCell(11).setCellValue("Nơi ở");
            headerRow.createCell(12).setCellValue("Họ tên bố");
            headerRow.createCell(13).setCellValue("Sđt bố");
            headerRow.createCell(14).setCellValue("Họ tên mẹ");
            headerRow.createCell(15).setCellValue("Sđt mẹ");

            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getSurname());
                row.createCell(2).setCellValue(student.getLastName());
                row.createCell(3).setCellValue(student.getCourse().getId());
                row.createCell(4).setCellValue(student.getMajor().getId());
                row.createCell(5).setCellValue(student.getClasses().getId());
                row.createCell(6).setCellValue(student.getDob());
                row.createCell(7).setCellValue(student.getGender());
                row.createCell(8).setCellValue(student.getPhoneNumber());
                row.createCell(9).setCellValue(student.getEmail());
                row.createCell(10).setCellValue(student.getHomeTown());
                row.createCell(11).setCellValue(student.getResidence());
                row.createCell(12).setCellValue(student.getFatherName());
                row.createCell(13).setCellValue(student.getFatherPhoneNumber());
                row.createCell(14).setCellValue(student.getMotherName());
                row.createCell(15).setCellValue(student.getMotherPhoneNumber());
            }

            FileOutputStream fos = new FileOutputStream("/upload/student.xlsx");
            workbook.write(fos);

            workbook.close();
            fos.close();
        } catch (IOException e){
            throw new RuntimeException("Không thể ghi file.");
        }
        return null;
    }

    private void createStudentStatus(Student student) {
        StudentStatus studentStatus = StudentStatus.builder()
                .studentId(student.getId())
                .surname(student.getSurname())
                .lastName(student.getLastName())
                .statusId(1)
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        int term = studentStatus.getTime().getMonth() >= 8 ? 2 : 1;
        int year = term == 1 ? studentStatus.getTime().getYear() + 1900 : studentStatus.getTime().getYear() + 1901;
        String termId = "" + year + term;
        studentStatus.setTermId(termId);
        studentStatusRepository.saveAndFlush(studentStatus);
    }

    private TrashStudent moveToTrash(Student student) {
        TrashStudent trashStudent = TrashStudent.builder()
                .student(student)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .build();
        trashStudentRepository.saveAndFlush(trashStudent);
        return trashStudent;
    }

    private void restoreFromTrash(Student student) {
        TrashStudent trashStudent = trashStudentRepository.findByStudent(student);
        trashStudentRepository.deleteById(trashStudent.getId());
    }

    private List<Student> checkIds(List<String> ids) {
        List<Student> students = new ArrayList<>();
        ids.forEach(id -> {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));
            students.add(student);
        });
        return students;
    }
}
