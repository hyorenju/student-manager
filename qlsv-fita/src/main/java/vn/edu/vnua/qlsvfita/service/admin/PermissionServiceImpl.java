package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Permission;
import vn.edu.vnua.qlsvfita.repository.PermissionRepository;
import vn.edu.vnua.qlsvfita.request.admin.permission.CreatePermissionRequest;
import vn.edu.vnua.qlsvfita.request.admin.permission.GetPermissionListRequest;
import vn.edu.vnua.qlsvfita.request.admin.permission.UpdatePermissionRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{
    private final PermissionRepository permissionRepository;

//    @Override
//    public Page<Permission> getPermissionList(GetPermissionListRequest request) {
//        return permissionRepository.findAll(PageRequest.of(request.getPage()-1, request.getSize()));
//    }

    @Override
    public List<Permission> getPermissionList() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission createPermission(CreatePermissionRequest request) {
        if(permissionRepository.existsById(request.getId())){
            throw new RuntimeException("Mã vai trò đã tồn tại trong hệ thống");
        }
        Permission permission = Permission.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        return  permissionRepository.saveAndFlush(permission);
    }

    @Override
    public Permission updatePermission(UpdatePermissionRequest request) {
        if(!permissionRepository.existsById(request.getId())){
            throw new RuntimeException("Mã vai trò không tồn tại trong hệ thống");
        }
        Permission permission = Permission.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        return  permissionRepository.saveAndFlush(permission);
    }

    @Override
    public void deletePermission(String id) {
        if(!permissionRepository.existsById(id)){
            throw new RuntimeException("Mã vai trò không tồn tại trong hệ thống");
        }
        permissionRepository.deleteById(id);
    }
}
