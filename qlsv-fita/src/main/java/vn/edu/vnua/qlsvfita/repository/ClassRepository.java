package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, String>, JpaSpecificationExecutor<Class> {
    Class getById(String id);
}
