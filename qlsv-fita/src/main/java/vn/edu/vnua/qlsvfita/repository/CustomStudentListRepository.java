package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.qlsvfita.model.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomStudentListRepository {
    public static Specification<Student> filterStudentList(String courseId,
                                                           String majorId,
                                                           String classId,
                                                           String status,
                                                           String studentId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(courseId)) {
                predicates.add(criteriaBuilder.like(root.get("course").get("id"), "K" + courseId));
            }
            if (StringUtils.hasText(majorId)) {
                predicates.add(criteriaBuilder.like(root.get("major").get("id"), majorId));
            }
            if (StringUtils.hasText(classId)) {
                predicates.add(criteriaBuilder.like(root.get("classes").get("id"), classId));
            }
            if (StringUtils.hasText(status)) {
                predicates.add(criteriaBuilder.like(root.get("status"), status));
            }
            if (StringUtils.hasText(studentId)) {
                predicates.add(criteriaBuilder.like(root.get("id"), studentId + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
