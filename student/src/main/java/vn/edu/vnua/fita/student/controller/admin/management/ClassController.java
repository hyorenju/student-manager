package vn.edu.vnua.fita.student.controller.admin.management;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.dto.ClassDTO;
import vn.edu.vnua.fita.student.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.classes.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.classes.UpdateClassRequest;
import vn.edu.vnua.fita.student.service.admin.management.ClassManager;
import vn.edu.vnua.fita.student.entity.table.Class;

import java.util.List;

@RestController
@RequestMapping("admin/class")
@RequiredArgsConstructor
public class ClassController extends BaseController {
    private final ClassManager classManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getClassList(@Valid @RequestBody GetClassListRequest request){
        Page<Class> page = classManager.getClassList(request);
        List<ClassDTO> response = page.getContent().stream().map(
                aClass -> modelMapper.map(aClass, ClassDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createClass(@Valid @RequestBody CreateClassRequest request){
        ClassDTO response = modelMapper.map(classManager.createClass(request), ClassDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    public ResponseEntity<?> createClass(@Valid @RequestBody UpdateClassRequest request){
        ClassDTO response = modelMapper.map(classManager.updateClass(request), ClassDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable String id){
        ClassDTO response = modelMapper.map(classManager.deleteClass(id), ClassDTO.class);
        return buildItemResponse(response);
    }
}
