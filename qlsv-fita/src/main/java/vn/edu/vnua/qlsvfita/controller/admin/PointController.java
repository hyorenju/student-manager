package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.PointListDTO;
import vn.edu.vnua.qlsvfita.model.dto.StudentListDTO;
import vn.edu.vnua.qlsvfita.model.dto.StudentTermDTO;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.admin.point.*;
import vn.edu.vnua.qlsvfita.service.admin.PointService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/point")
@RequiredArgsConstructor
public class PointController extends BaseController {
    private final PointService pointService;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_POINT_LIST')")
    public ResponseEntity<?> getPointList(@Valid @RequestBody GetPointListRequest request){
        Page<StudentTerm> page = pointService.filterPointList(request);
        List<PointListDTO> response = page.getContent().stream().map(
                point -> modelMapper.map(point, PointListDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_POINT')")
    public ResponseEntity<?> createPoint(@Valid @RequestBody CreatePointRequest request){
        StudentTermDTO response = pointService.createPoint(request);
        return buildItemResponse(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_POINT')")
    public ResponseEntity<?> updatePoint(@Valid @RequestBody UpdatePointRequest request){
        StudentTermDTO response = pointService.updatePoint(request);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT')")
    public ResponseEntity<?> deletePoint(@Valid @RequestBody DeletePointRequest request){
        StudentTermDTO response = pointService.deletePoint(request);
        return buildItemResponse(response);
    }

    @PostMapping("export")
    public ResponseEntity<?> exportPointList(@Valid @RequestBody ExportPointListRequest request){
        List<PointListDTO> response = pointService.exportPointList(request).stream().map(
                point -> modelMapper.map(point, PointListDTO.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('IMPORT_POINT_LIST')")
    public ResponseEntity<?> importPointList(MultipartFile file) throws IOException {
        pointService.importStudyPoint(file);
        return buildItemResponse("Nhập dữ liệu thành công");
    }
}
