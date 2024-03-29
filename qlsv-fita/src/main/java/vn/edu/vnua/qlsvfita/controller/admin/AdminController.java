package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.AdminDetailDTO;
import vn.edu.vnua.qlsvfita.model.dto.AdminListDTO;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.request.admin.admin.CreateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.GetAdminListRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateMyselfRequest;
import vn.edu.vnua.qlsvfita.service.admin.AdminService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/admin")
@RequiredArgsConstructor
public class AdminController extends BaseController {
    private final AdminService adminService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_ADMIN_LIST')")
    public ResponseEntity<?> getAdminList(@Valid @RequestBody GetAdminListRequest request){
        Page<Admin> page = adminService.getAdminList(request);
        List<AdminListDTO> response = page.getContent().stream().map(
                admin -> modelMapper.map(admin, AdminListDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("detail/{id}")
    public ResponseEntity<?> getAdminDetail(@PathVariable String id){
        AdminDetailDTO response = modelMapper.map(adminService.getAdminDetail(id), AdminDetailDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdminRequest request){
        AdminListDTO response = modelMapper.map(adminService.createAdmin(request), AdminListDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
    public ResponseEntity<?> updateAdmin(@Valid @RequestBody UpdateAdminRequest request){
        AdminDetailDTO response = modelMapper.map(adminService.updateAdmin(request), AdminDetailDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("edit")
    public ResponseEntity<?> updateMyself(@RequestBody UpdateMyselfRequest request){
        AdminDetailDTO response = modelMapper.map(adminService.updateMyself(request), AdminDetailDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("avatar/{id}")
    public ResponseEntity<?> updateAvatar(@PathVariable String id, MultipartFile file){
        AdminDetailDTO response = modelMapper.map(adminService.updateAvatar(id, file), AdminDetailDTO.class);
        return buildItemResponse(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
    public ResponseEntity<?> deleteAdmin(@PathVariable String id){
        adminService.deleteAdmin(id);
        String message = "Xóa thành công admin "+id;
        return buildItemResponse(message);
    }
}
