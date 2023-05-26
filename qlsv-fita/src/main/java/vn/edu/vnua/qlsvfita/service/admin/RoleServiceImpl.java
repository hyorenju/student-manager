package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.vnua.qlsvfita.model.dto.AdminPermissionListDTO;
import vn.edu.vnua.qlsvfita.model.dto.RoleDTO;
import vn.edu.vnua.qlsvfita.model.entity.Permission;
import vn.edu.vnua.qlsvfita.model.entity.Role;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.repository.PermissionRepository;
import vn.edu.vnua.qlsvfita.repository.RoleRepository;
import vn.edu.vnua.qlsvfita.request.admin.role.CreateRoleRequest;
import vn.edu.vnua.qlsvfita.request.admin.role.GetRoleListRequest;
import vn.edu.vnua.qlsvfita.request.admin.role.UpdateRoleRequest;
import vn.edu.vnua.qlsvfita.response.PermissionCheckResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public RoleDTO createRole(CreateRoleRequest request) {
        if(roleRepository.existsById(request.getId())){
            throw new RuntimeException("Mã quyền đã tồn tại trong hệ thống");
        }
        checkPermissionIsValid(request.getPermissionIds());
        Role role = Role.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        List<Permission> permissions = buildPermission(request.getPermissionIds());
        role.setPermissions(permissions);

        roleRepository.save(role);
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(UpdateRoleRequest request) {
        if(!roleRepository.existsById(request.getId())){
            throw new RuntimeException("Mã quyền không tồn tại trong hệ thống");
        }
        checkPermissionIsValid(request.getPermissionIds());
        validateRoleExist(request.getId());
        Optional<Role> role = roleRepository.findById(request.getId());
        if (role.isPresent()) {
            List<Permission> permissions = buildPermission(request.getPermissionIds());
            Role roleUpdatePojo = role.get();
//            roleUpdatePojo.setName(request.getName());
            roleUpdatePojo.setPermissions(permissions);
            return modelMapper.map(roleRepository.save(roleUpdatePojo), RoleDTO.class);
        }
        throw new RuntimeException("Có lỗi xảy ra trong quá trình update vai trò");
    }

    @Override
    public Page<Role> getRoleList(GetRoleListRequest request) {
        return roleRepository.findAll(PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Role getRole(String id) {
        return roleRepository.getById(id);
    }


    @Override
    public List<AdminPermissionListDTO> getAdminPermission(String roleId) {
//        List<Permission> permissions = permissionRepository.findAll();
//        Role admin = roleRepository.getById(roleId);
//
//        AdminPermissionListDTO adminPermission = new AdminPermissionListDTO();
//        adminPermission.setRoleId(roleId);
//        List<Boolean> adminPermissions = null;
//        adminPermission.setPermissions(adminPermissions);
//
//        adminPermissions = new ArrayList<>();
//        for (Permission permission :
//                permissions) {
//            String permissionId = permission.getId();
//            adminPermissions.add();
//            if (admin.getPermissions().contains(permission)) {
//
//            }
//        }

        return null;
    }

    @Override
    public PermissionCheckResponse authorization(Collection<SimpleGrantedAuthority> authorities) {
        if(CollectionUtils.isEmpty(authorities))
            throw new RuntimeException("Not found of user authorities");

        List<Permission> permissionList = permissionRepository.findAll();
        if(CollectionUtils.isEmpty(permissionList))
            throw new RuntimeException("Not found of user permissions");

        PermissionCheckResponse response = new PermissionCheckResponse();
        Map<String, Boolean> permissions = new HashMap<>();

        permissionList.stream().forEach(permission -> {
            permissions.put( permission.getId(),
                    authorities.stream().anyMatch(simpleGrantedAuthority -> permission.getId().equalsIgnoreCase(simpleGrantedAuthority.getAuthority())));
        });
        response.setCheckingPermissions(permissions);

        return response;
    }

    @Override
    public PermissionCheckResponse checkingPermissions(String id) {
        if(!roleRepository.existsById(id)){
            throw new RuntimeException("Không tồn tại vai trò "+id+" trong hệ thống");
        }

        List<Permission> permissionList = permissionRepository.findAll();
        PermissionCheckResponse response = new PermissionCheckResponse();
        Map<String, Boolean> permissions = new HashMap<>();

        permissionList.stream().forEach(
                permission -> {
                    permissions.put(permission.getId(), null);
                    for (Permission anotherPermission:
                         roleRepository.getById(id).getPermissions()) {
                        if(Objects.equals(permission.getId(), anotherPermission.getId())){
                            permissions.put(permission.getId(), true);
                        }
                    }
                });

        response.setCheckingPermissions(permissions);

        return response;
    }

    @Override
    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }






//Authen

    private void checkPermissionIsValid(List<String> permissionIds) {
        List<Permission> permissions = buildPermission(permissionIds);
        if (CollectionUtils.isEmpty(permissions)) {
            throw new RuntimeException("Permisison không tồn tại");
        }
        List<String> listIdExists = permissions.stream().map(Permission::getId).collect(Collectors.toList());
        List<String> idNotExists = permissionIds.stream().filter(s -> !listIdExists.contains(s)
        ).collect(Collectors.toList());
        if (!idNotExists.isEmpty())
            throw new RuntimeException(String.format("Trong danh sách permisison ids có mã không tồn tại trên hệ thống: %s", idNotExists));
    }

    private List<Permission> buildPermission(List<String> permissionIds) {
        return permissionRepository.findAllById(permissionIds);
    }

    private void validateRoleExist(String id) {
        boolean isExist = roleRepository.existsById(id);
        if (!isExist)
            throw new RuntimeException("Vai trò không tồn tại trên hệ thống");
    }

}
