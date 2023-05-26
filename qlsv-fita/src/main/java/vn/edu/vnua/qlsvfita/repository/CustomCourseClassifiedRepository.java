package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.model.entity.CourseTerm;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCourseClassifiedRepository {
    public static Specification<CourseTerm> filterCourseClassified(String courseId,
                                                                  String termId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(courseId!=null){
                predicates.add(criteriaBuilder.like(root.get("courseId"), "%"+ courseId +"%"));
            }
            if(termId!=null) {
                predicates.add(criteriaBuilder.like(root.get("termId"), "%"+ termId +"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
