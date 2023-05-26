package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.model.entity.Term;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<StudentTerm, Long>, JpaSpecificationExecutor<StudentTerm> {
    boolean existsByStudentIdAndTermId(String studentId, String termId);
    boolean existsByTermId(String termId);
    StudentTerm getByStudentIdAndTermId(String studentId, String termId);
    StudentTerm getByStudentId(String studentId);
    List<StudentTerm> findAllByStudentId(String studentId);
    List<StudentTerm> findAllByTermId(String termId);
}
