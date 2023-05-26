package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vn.edu.vnua.qlsvfita.model.dto.AdminPermissionListDTO;
import vn.edu.vnua.qlsvfita.model.dto.RoleDTO;
import vn.edu.vnua.qlsvfita.model.entity.Role;
import vn.edu.vnua.qlsvfita.request.admin.role.CreateRoleRequest;
import vn.edu.vnua.qlsvfita.request.admin.role.GetRoleListRequest;
import vn.edu.vnua.qlsvfita.request.admin.role.UpdateRoleRequest;
import vn.edu.vnua.qlsvfita.response.PermissionCheckResponse;

import java.util.Collection;
import java.util.List;

public interface RoleService {
    RoleDTO createRole(CreateRoleRequest request);

    RoleDTO updateRole(UpdateRoleRequest request);

    Page<Role> getRoleList(GetRoleListRequest request);
    Role getRole(String id);
    List<AdminPermissionListDTO> getAdminPermission(String roleId);
    PermissionCheckResponse authorization(Collection<SimpleGrantedAuthority> authorities);
    PermissionCheckResponse checkingPermissions(String id);
    void deleteRole(String id);
}
