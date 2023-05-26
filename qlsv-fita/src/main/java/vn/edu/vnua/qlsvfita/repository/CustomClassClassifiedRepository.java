package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomClassClassifiedRepository {
    public static Specification<ClassTerm> filterClassClassified(String classId,
                                                                 String termId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(classId!=null){
                predicates.add(criteriaBuilder.like(root.get("classId"), "%"+ classId +"%"));
            }
            if(termId!=null) {
                predicates.add(criteriaBuilder.like(root.get("termId"), "%"+ termId +"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
