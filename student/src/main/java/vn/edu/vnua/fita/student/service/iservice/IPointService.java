package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.manager.trash.TrashPoint;
import vn.edu.vnua.fita.student.entity.table.Point;
import vn.edu.vnua.fita.student.entity.table.Student;
import vn.edu.vnua.fita.student.request.admin.point.*;

import java.util.List;


public interface IPointService {
    Page<Point> filterPointList(GetPointListRequest request);
    Point createPoint(CreatePointRequest request);
    Point updatePoint(Long id, UpdatePointRequest request);
    TrashPoint deletePoint(Long id);
    List<TrashPoint> deleteManyPoint(DeletePointRequest request);
    TrashPoint restorePoint(Long id);
    List<TrashPoint> restoreManyPoint(RestorePointRequest request);
    Page<TrashPoint> getTrashPointList(GetTrashPointRequest request);
}
