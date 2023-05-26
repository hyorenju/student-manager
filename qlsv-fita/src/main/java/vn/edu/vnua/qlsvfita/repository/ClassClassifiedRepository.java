package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;

import java.util.List;

@Repository
public interface ClassClassifiedRepository extends JpaRepository<ClassTerm, Long>, JpaSpecificationExecutor<ClassTerm> {
    boolean existsByClassIdAndTermId(String classId, String termId);

    ClassTerm findByClassIdAndTermId(String classId, String termId);
}
