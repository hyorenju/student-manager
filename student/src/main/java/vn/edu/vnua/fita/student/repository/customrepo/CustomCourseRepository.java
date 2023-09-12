package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.entity.table.Course;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCourseRepository {
    public static Specification<Course> filterCourseList(Long courseId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (courseId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), courseId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
