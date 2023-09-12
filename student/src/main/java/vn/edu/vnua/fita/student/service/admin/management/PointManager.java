package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.entity.manager.trash.TrashPoint;
import vn.edu.vnua.fita.student.entity.table.Point;
import vn.edu.vnua.fita.student.entity.table.Student;
import vn.edu.vnua.fita.student.repository.customrepo.CustomPointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.request.admin.point.*;
import vn.edu.vnua.fita.student.service.iservice.IPointService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointManager implements IPointService {
    private final PointRepository pointRepository;
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final TrashPointRepository trashPointRepository;
    private final String pointHadExistedMsg = "Dữ liệu của sinh viên %s trong học kỳ %s đã tồn tại trong hệ thống";
    private final String studentNotFoundMsg = "Mã sinh viên %s không tồn tại trong hệ thống";
    private final String termNotFoundMsg = "Học kỳ %s không tồn tại trong hệ thống";
    private final String pointNotFoundMsg = "Không tìm thấy mã điểm %d";

    @Override
    public Page<Point> filterPointList(GetPointListRequest request) {
        Specification<Point> specification = CustomPointRepository.filterPointList(
                request.getStudentId(),
                request.getTermId(),
                request.getFilter().getPoint(),
                request.getFilter().getAccPoint(),
                request.getFilter().getTrainingPoint()
        );
        return pointRepository.findAll(
                specification,
                PageRequest.of(request.getPage() - 1, request.getSize())
        );
    }

    @Override
    public Point createPoint(CreatePointRequest request) {
        if(pointRepository.existsByStudentIdAndTermId(request.getStudentId(), request.getTermId())){
            throw new RuntimeException(String.format(pointHadExistedMsg, request.getStudentId(), request.getTermId()));
        }
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, request.getStudentId())));
        termRepository.findById(request.getTermId()).orElseThrow(() -> new RuntimeException(String.format(termNotFoundMsg, request.getTermId())));
        Point point = Point.builder()
                .studentId(request.getStudentId())
                .surname(student.getSurname())
                .lastName(student.getLastName())
                .termId(request.getTermId())
                .avgPoint10(request.getAvgPoint10())
                .avgPoint4(request.getAvgPoint4())
                .trainingPoint(request.getTrainingPoint())
                .creditsAcc(request.getCreditsAcc())
                .pointAcc10(request.getPointAcc10())
                .pointAcc4(request.getPointAcc4())
                .isDeleted(false)
                .build();

        return pointRepository.saveAndFlush(point);
    }

    @Override
    public Point updatePoint(Long id, UpdatePointRequest request) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointHadExistedMsg, request.getStudentId(), request.getTermId())));
        studentRepository.findById(request.getStudentId()).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, request.getStudentId())));
        termRepository.findById(request.getTermId()).orElseThrow(() -> new RuntimeException(String.format(termNotFoundMsg, request.getTermId())));

        point.setStudentId(request.getStudentId());
        point.setTermId(request.getTermId());
        point.setAvgPoint10(request.getAvgPoint10());
        point.setAvgPoint4(request.getAvgPoint4());
        point.setTrainingPoint(request.getTrainingPoint());
        point.setCreditsAcc(request.getCreditsAcc());
        point.setPointAcc10(request.getPointAcc10());
        point.setPointAcc4(request.getPointAcc4());

        return pointRepository.saveAndFlush(point);
    }

    @Override
    public TrashPoint deletePoint(Long id) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
        point.setIsDeleted(true);
        TrashPoint trashPoint = moveToTrash(point);
        pointRepository.saveAndFlush(point);
        return trashPoint;
    }

    @Override
    public List<TrashPoint> deleteManyPoint(DeletePointRequest request) {
        List<Point> points = checkIds(request.getIds());
        List<TrashPoint> trashPoints = new ArrayList<>();
        points.forEach(point -> {
            point.setIsDeleted(true);
            pointRepository.saveAndFlush(point);
            trashPoints.add(moveToTrash(point));
        });
        return trashPoints;
    }

    @Override
    public TrashPoint restorePoint(Long id) {
        TrashPoint trashPoint = trashPointRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác muốn khôi phục"));
        Point point = trashPoint.getPoint();
        point.setIsDeleted(false);
        restoreFromTrash(point);
        pointRepository.saveAndFlush(point);
        return trashPoint;
    }

    @Override
    public List<TrashPoint> restoreManyPoint(RestorePointRequest request) {
        List<TrashPoint> trashPoints = trashPointRepository.findAllById(request.getIds());
        trashPoints.forEach(trashPoint -> {
            Point point = trashPoint.getPoint();
            point.setIsDeleted(false);
            restoreFromTrash(point);
            pointRepository.saveAndFlush(point);
        });
        return trashPoints;
    }

    @Override
    public Page<TrashPoint> getTrashPointList(GetTrashPointRequest request) {
        return trashPointRepository.findAll(PageRequest.of(request.getPage()-1, request.getSize(), Sort.by("id").descending()));
    }

    private TrashPoint moveToTrash(Point point) {
        TrashPoint trashPoint = TrashPoint.builder()
                .point(point)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .build();
        trashPointRepository.saveAndFlush(trashPoint);
        return trashPoint;
    }

    private void restoreFromTrash(Point point) {
        TrashPoint trashPoint = trashPointRepository.findByPoint(point);
        trashPointRepository.deleteById(trashPoint.getId());
    }

    private List<Point> checkIds(List<Long> ids) {
        List<Point> points = new ArrayList<>();
        ids.forEach(id -> {
            Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
            points.add(point);
        });
        return points;
    }
}
