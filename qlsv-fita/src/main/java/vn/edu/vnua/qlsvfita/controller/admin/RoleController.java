package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.UserDetailsImpl;
import vn.edu.vnua.qlsvfita.model.dto.RoleDTO;
import vn.edu.vnua.qlsvfita.model.entity.Role;
import vn.edu.vnua.qlsvfita.request.admin.role.CreateRoleRequest;
import vn.edu.vnua.qlsvfita.request.admin.role.GetRoleListRequest;
import vn.edu.vnua.qlsvfita.request.admin.role.UpdateRoleRequest;
import vn.edu.vnua.qlsvfita.response.BaseResponse;
import vn.edu.vnua.qlsvfita.response.PermissionCheckResponse;
import vn.edu.vnua.qlsvfita.service.admin.RoleService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {
    private final RoleService roleService;
    private final ModelMapper modelMapper = new ModelMapper();


    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_ROLE')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateRoleRequest request) {
        RoleDTO response = roleService.createRole(request);
        return buildItemResponse(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_ROLE')")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateRoleRequest request) {
        RoleDTO response = roleService.updateRole(request);
        return buildItemResponse(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_ROLE')")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        roleService.deleteRole(id);
        String response = "Xóa thành công quyền " + id;
        return buildItemResponse(response);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('GET_ROLE_LIST')")
    public ResponseEntity<?> getRoles(@Valid @RequestBody GetRoleListRequest request) {
        Page<Role> page = roleService.getRoleList(request);
        List<RoleDTO> response = page.getContent().stream().map(
                role -> modelMapper.map(role, RoleDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("")
    public ResponseEntity<?> getRole() {

        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

//        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getDetails();
        return buildItemResponse(roleService.authorization((Collection<SimpleGrantedAuthority>) authentication.getAuthorities()));
    }

    @PostMapping("{id}")
    public ResponseEntity<?> getCheckingPermissions(@PathVariable String id) {
        PermissionCheckResponse response =  roleService.checkingPermissions(id);
        return buildItemResponse(response);
    }
}