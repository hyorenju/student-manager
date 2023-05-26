package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.dto.StudentTermDTO;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.model.entity.Term;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.point.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
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
}
