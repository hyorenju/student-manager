package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Course;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCourseListRepository {
    public static Specification<Course> filterCourseList(String courseId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (courseId != null) {
                if (StringUtils.hasText(courseId)) {
                    predicates.add(criteriaBuilder.like(root.get("id"), "K"+courseId));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
