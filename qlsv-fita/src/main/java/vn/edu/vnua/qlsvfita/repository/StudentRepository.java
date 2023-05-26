package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Course;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.Class;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
    Student getStudentById(String id);
    List<Student> findAllByStatus(String status);
    boolean existsByEmail(String email);
    List<Student> findAllByClassesAndStatus(Class classes, String status);
    List<Student> findAllByCourseAndStatus(Course course, String status);
    List<Student> findAllByCourse(Course course);
    List<Student> findAllByClasses(Class classes);
    List<Student> findAllByOrderByNameAsc();

}
