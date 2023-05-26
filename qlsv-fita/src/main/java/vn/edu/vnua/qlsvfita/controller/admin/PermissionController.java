package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.PermissionDTO;
import vn.edu.vnua.qlsvfita.model.entity.Permission;
import vn.edu.vnua.qlsvfita.request.admin.permission.CreatePermissionRequest;
import vn.edu.vnua.qlsvfita.request.admin.permission.GetPermissionListRequest;
import vn.edu.vnua.qlsvfita.request.admin.permission.UpdatePermissionRequest;
import vn.edu.vnua.qlsvfita.service.admin.PermissionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/permission")
@RequiredArgsConstructor
public class PermissionController extends BaseController {
    private final PermissionService permissionService;
    private final ModelMapper modelMapper = new ModelMapper();

//    @GetMapping("list")
//    @PreAuthorize("hasAnyAuthority('GET_PERMISSION_LIST')")
//    public ResponseEntity<?> getPermissionList(@Valid @RequestBody GetPermissionListRequest request){
//        Page<Permission> page = permissionService.getPermissionList(request);
//        List<PermissionDTO> response = page.getContent().stream().map(
//                permission -> modelMapper.map(permission, PermissionDTO.class)
//        ).collect(Collectors.toList());
//        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
//
//    }

    @GetMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_PERMISSION_LIST')")
    public ResponseEntity<?> getPermissionList(){
        List<PermissionDTO> response = permissionService.getPermissionList().stream().map(
                permission -> modelMapper.map(permission, PermissionDTO.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_PERMISSION')")
    public ResponseEntity<?> createPermission(@Valid @RequestBody CreatePermissionRequest request){
        PermissionDTO response = modelMapper.map(permissionService.createPermission(request), PermissionDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_PERMISSION')")
    public ResponseEntity<?> updatePermission(@Valid @RequestBody UpdatePermissionRequest request){
        PermissionDTO response = modelMapper.map(permissionService.updatePermission(request), PermissionDTO.class);
        return buildItemResponse(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_PERMISSION')")
    public ResponseEntity<?> deletePermission(@PathVariable String id){
        permissionService.deletePermission(id);
        String response = "Xóa thành công vai trò "+id;
        return buildItemResponse(response);
    }
}
