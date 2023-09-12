package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.manager.trash.TrashPoint;
import vn.edu.vnua.fita.student.entity.table.Point;

@Repository
public interface TrashPointRepository extends JpaRepository<TrashPoint, Long> {
    TrashPoint findByPoint(Point point);
}
