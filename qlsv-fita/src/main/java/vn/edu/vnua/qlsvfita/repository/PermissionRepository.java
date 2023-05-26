package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {
}
