package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.MajorListDTO;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.GetMajorListRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.UpdateMajorRequest;
import vn.edu.vnua.qlsvfita.service.admin.MajorService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/major")
@RequiredArgsConstructor
public class MajorController extends BaseController {
    private final MajorService majorService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_MAJOR_LIST')")
    public ResponseEntity<?> getMajorList(@Valid @RequestBody GetMajorListRequest request){
        Page<Major> page = majorService.getMajorList(request);
        List<MajorListDTO> response = page.getContent().stream().map(
                major -> modelMapper.map(major, MajorListDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_MAJOR')")
    public ResponseEntity<?> createMajor(@Valid @RequestBody CreateMajorRequest request){
        MajorListDTO response = modelMapper.map(majorService.createMajor(request), MajorListDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_MAJOR')")
    public ResponseEntity<?> updateMajor(@Valid @RequestBody UpdateMajorRequest request, @PathVariable String id){
        MajorListDTO response = modelMapper.map(majorService.updateMajor(request, id), MajorListDTO.class);
        return buildItemResponse(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_MAJOR')")
    public ResponseEntity<?> deleteMajor(@PathVariable String id){
        majorService.deleteMajor(id);
        String message = "Xóa thành công chuyên ngành "+id;
        return buildItemResponse(message);
    }
}
