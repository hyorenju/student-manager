package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Class;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomClassListRepository {
    public static Specification<Class> filterClassList(String classId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (classId != null) {
                if (classId.contains("K\\d")) {
                    predicates.add(criteriaBuilder.like(root.get("id"), classId + "%"));
                } else {
                    predicates.add(criteriaBuilder.like(root.get("id"), "%" + classId + "%"));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
