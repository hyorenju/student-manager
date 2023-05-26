package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.model.entity.Role;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    Page<Admin> getAllByRoleIsNot(Role role, Pageable pageable);
    boolean existsByEmail(String email);
    Admin getById(String id);
}
