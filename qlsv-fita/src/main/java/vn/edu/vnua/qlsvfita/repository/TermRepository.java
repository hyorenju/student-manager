package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, String>, JpaSpecificationExecutor<Term> {
    Term getById(String id);
}
