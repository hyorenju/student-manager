package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.authorization.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
