package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.ClassListDTO;
import vn.edu.vnua.qlsvfita.model.dto.classic.ClassDTO;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.qlsvfita.request.admin.classes.GetClassListRequest;
import vn.edu.vnua.qlsvfita.request.admin.classes.UpdateClassRequest;
import vn.edu.vnua.qlsvfita.response.BaseListItemResponse;
import vn.edu.vnua.qlsvfita.service.admin.ClassService;
import vn.edu.vnua.qlsvfita.service.admin.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/class")
@RequiredArgsConstructor
public class ClassController extends BaseController {
    private final ClassService classService;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_CLASS_LIST')")
    public ResponseEntity<?> getStudentList(@Valid @RequestBody GetClassListRequest request) {
        Page<Class> page = classService.getClassList(request);
        List<ClassListDTO> response = page.getContent().stream().map(
                classes -> modelMapper.map(classes, ClassListDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_CLASS')")
    public ResponseEntity<?> createClass(@Valid @RequestBody CreateClassRequest request){
        Class aClass = classService.createClass(request);
        ClassListDTO response = modelMapper.map(aClass, ClassListDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_CLASS')")
    public ResponseEntity<?> updateClass(@Valid @RequestBody UpdateClassRequest request, @PathVariable String id){
        ClassListDTO response = modelMapper.map(classService.updateClass(request, id), ClassListDTO.class);
        return buildItemResponse(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_CLASS')")
    public ResponseEntity<?> deleteClass(@PathVariable String id){
        classService.deleteClass(id);
        String message = "Xóa thành công mã lớp "+id;
        return buildItemResponse(message);
    }
}
