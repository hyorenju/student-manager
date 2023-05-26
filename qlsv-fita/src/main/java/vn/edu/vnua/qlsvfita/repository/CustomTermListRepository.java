package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.qlsvfita.model.entity.Course;
import vn.edu.vnua.qlsvfita.model.entity.Term;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomTermListRepository {
    public static Specification<Term> filterTermList(String termId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(termId)) {
                predicates.add(criteriaBuilder.like(root.get("id"), termId+"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
