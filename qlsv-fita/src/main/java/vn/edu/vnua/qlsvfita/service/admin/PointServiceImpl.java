package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.model.dto.StudentTermDTO;
import vn.edu.vnua.qlsvfita.model.entity.*;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.point.*;
import vn.edu.vnua.qlsvfita.util.MyUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final MajorRepository majorRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<StudentTerm> filterPointList(GetPointListRequest request) {
        Specification<StudentTerm> specification = CustomPointListRepository.filterPointList(
                request.getStudentId(),
                request.getTermId(),
                request.getFilter().getPoint(),
                request.getFilter().getAccPoint(),
                request.getFilter().getTrainingPoint()

//                request.getFilter().getPoint().getMin(),
//                request.getFilter().getPoint().getMax(),
//                request.getFilter().getAccPoint().getMin(),
//                request.getFilter().getAccPoint().getMax(),
//                request.getFilter().getTrainingPoint().getMin(),
//                request.getFilter().getTrainingPoint().getMax()
        );
        return pointRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public StudentTermDTO createPoint(CreatePointRequest request) {
        if (pointRepository.existsByStudentIdAndTermId(request.getStudentId(), request.getTermId())) {
            throw new RuntimeException("Dữ liệu của sinh viên " + request.getStudentId() + " trong học kỳ " + request.getTermId() + " đã tồn tại trong hệ thống");
        } else if (!studentRepository.existsById(request.getStudentId())) {
            throw new RuntimeException("Mã sinh viên " + request.getStudentId() + " không tồn tại trong hệ thống");
        } else if (!termRepository.existsById(request.getTermId())) {
            throw new RuntimeException("Học kỳ " + request.getTermId() + " không tồn tại trong hệ thống");
        }
        StudentTerm point = StudentTerm.builder()
                .studentId(request.getStudentId())
                .studentName(studentRepository.getStudentById(request.getStudentId()).getName())
                .termId(request.getTermId())
                .medScore10(request.getMedScore10())
                .medScore4(request.getMedScore4())
                .trainingPoint(request.getTrainingPoint())
                .creditsAccumulated(request.getCreditsAccumulated())
                .scoreAccumulated10(request.getScoreAccumulated10())
                .scoreAccumulated4(request.getScoreAccumulated4())
                .build();

        point = pointRepository.saveAndFlush(point);
        return modelMapper.map(point, StudentTermDTO.class);
    }

    @Override
    public StudentTermDTO updatePoint(UpdatePointRequest request) {
        if (!pointRepository.existsByStudentIdAndTermId(request.getStudentId(), request.getTermId())) {
            throw new RuntimeException("Dữ liệu của sinh viên " + request.getStudentId() + " trong học kỳ " + request.getTermId() + " không tồn tại trong hệ thống");
        } else {
            StudentTerm pointEdited = StudentTerm.builder()
                    .id(pointRepository.getByStudentIdAndTermId(request.getStudentId(), request.getTermId()).getId())
                    .studentId(request.getStudentId())
                    .studentName(studentRepository.getStudentById(request.getStudentId()).getName())
                    .termId(request.getTermId())
                    .medScore10(request.getMedScore10())
                    .medScore4(request.getMedScore4())
                    .trainingPoint(request.getTrainingPoint())
                    .creditsAccumulated(request.getCreditsAccumulated())
                    .scoreAccumulated10(request.getScoreAccumulated10())
                    .scoreAccumulated4(request.getScoreAccumulated4())
                    .build();
            pointEdited = pointRepository.saveAndFlush(pointEdited);
            return modelMapper.map(pointEdited, StudentTermDTO.class);
        }
    }

    @Override
    public StudentTermDTO deletePoint(DeletePointRequest request) {
        if (!pointRepository.existsByStudentIdAndTermId(request.getStudentId(), request.getTermId())) {
            throw new RuntimeException("Dữ liệu của sinh viên " + request.getStudentId() + " trong học kỳ " + request.getTermId() + " không tồn tại trong hệ thống");
        } else {
            StudentTerm point = pointRepository.getByStudentIdAndTermId(request.getStudentId(), request.getTermId());
            pointRepository.deleteById(point.getId());
            return modelMapper.map(point, StudentTermDTO.class);
        }
    }

    @Override
    public List<StudentTerm> exportPointList(ExportPointListRequest request) {
        Specification<StudentTerm> specification = CustomPointListRepository.filterPointList(
                request.getStudentId(),
                request.getTermId(),
                request.getFilter().getPoint(),
                request.getFilter().getAccPoint(),
                request.getFilter().getTrainingPoint()

//                request.getFilter().getPoint().getMin(),
//                request.getFilter().getPoint().getMax(),
//                request.getFilter().getAccPoint().getMin(),
//                request.getFilter().getAccPoint().getMax(),
//                request.getFilter().getTrainingPoint().getMin(),
//                request.getFilter().getTrainingPoint().getMax()
        );
        return pointRepository.findAll(specification);
    }

    @Override
    public void importStudyPoint(MultipartFile file) throws IOException {
        List<StudentTerm> points = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue; // Skip header row
            }

            if (pointRepository.existsByStudentIdAndTermId(String.valueOf((int) row.getCell(0).getNumericCellValue()), String.valueOf((int) row.getCell(6).getNumericCellValue()))) {
                throw new RuntimeException("Dữ liệu của sinh viên " + (int)row.getCell(0).getNumericCellValue() + " trong học kỳ " + (int)row.getCell(6).getNumericCellValue() + " đã tồn tại trong hệ thống");
            } else if (!studentRepository.existsById(String.valueOf((int) row.getCell(0).getNumericCellValue()))) {
                throw new RuntimeException("Mã sv " + (int)row.getCell(0).getNumericCellValue() + " không tồn tại");
            } else if (!termRepository.existsById(String.valueOf((int) row.getCell(6).getNumericCellValue()))) {
                throw new RuntimeException("Mã học kỳ " + (int)row.getCell(6).getNumericCellValue() + " không tồn tại");
            }

            Student student = studentRepository.getStudentById(String.valueOf((int) row.getCell(0).getNumericCellValue()));
            student.setGender(row.getCell(4).getStringCellValue());
            for (Major major : majorRepository.findAll()) {
                if ((row.getCell(5).getStringCellValue().contains(major.getId()))) {
                    student.setMajor(Major.builder().id(major.getId()).build());
                    student.setCourse(Course.builder().id(row.getCell(5).getStringCellValue().split(major.getId())[0]).build());
                }
            }
            student.setClasses(Class.builder().id(row.getCell(5).getStringCellValue()).build());
            students.add(student);

            StudentTerm point = new StudentTerm();
            if (pointRepository.existsByStudentIdAndTermId(String.valueOf((int) row.getCell(0).getNumericCellValue()), String.valueOf((int) row.getCell(6).getNumericCellValue()))) {
                point.setId(pointRepository.getByStudentIdAndTermId(String.valueOf((int) row.getCell(0).getNumericCellValue()), String.valueOf((int) row.getCell(6).getNumericCellValue())).getId());
            }
            point.setStudentId(String.valueOf((int) row.getCell(0).getNumericCellValue()));
            String name = row.getCell(1).getStringCellValue() + " " + row.getCell(2).getStringCellValue();
            point.setStudentName(name);
            point.setTermId(String.valueOf((int) row.getCell(6).getNumericCellValue()));
            point.setMedScore10((float) row.getCell(7).getNumericCellValue());
            point.setMedScore4((float) row.getCell(8).getNumericCellValue());
            point.setScoreAccumulated10((float) row.getCell(9).getNumericCellValue());
            point.setScoreAccumulated4((float) row.getCell(10).getNumericCellValue());
            point.setCreditsAccumulated((int) row.getCell(11).getNumericCellValue());
            point.setTrainingPoint((int) row.getCell(12).getNumericCellValue());
            points.add(point);
        }
        workbook.close();

        // Lưu sản phẩm vào CSDL
        studentRepository.saveAll(students);
        pointRepository.saveAll(points);
    }
}
