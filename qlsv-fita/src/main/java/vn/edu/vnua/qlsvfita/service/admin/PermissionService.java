package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.Permission;
import vn.edu.vnua.qlsvfita.request.admin.permission.CreatePermissionRequest;
import vn.edu.vnua.qlsvfita.request.admin.permission.GetPermissionListRequest;
import vn.edu.vnua.qlsvfita.request.admin.permission.UpdatePermissionRequest;

import java.util.List;

public interface PermissionService {
//    Page<Permission> getPermissionList(GetPermissionListRequest request);
    List<Permission> getPermissionList();

    Permission createPermission(CreatePermissionRequest request);

    Permission updatePermission(UpdatePermissionRequest request);

    void deletePermission(String id);
}
