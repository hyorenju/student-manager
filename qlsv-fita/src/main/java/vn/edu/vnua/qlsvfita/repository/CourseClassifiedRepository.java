package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.model.entity.CourseTerm;

@Repository
public interface CourseClassifiedRepository extends JpaRepository<CourseTerm, Long>, JpaSpecificationExecutor<CourseTerm> {
    boolean existsByCourseIdAndTermId(String courseId, String termId);

    CourseTerm findByCourseIdAndTermId(String courseId, String termId);
}
