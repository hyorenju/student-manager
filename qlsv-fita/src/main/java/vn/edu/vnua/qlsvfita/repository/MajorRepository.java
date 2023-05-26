package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, String>, JpaSpecificationExecutor<Major> {
    Major getById(String id);
}
