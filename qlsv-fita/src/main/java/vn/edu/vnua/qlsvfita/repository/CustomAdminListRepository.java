package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.model.entity.Student;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomAdminListRepository {
    public static Specification<Admin> filterAdminList(String id) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(id)) {
                predicates.add(criteriaBuilder.like(root.get("id"), id + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
