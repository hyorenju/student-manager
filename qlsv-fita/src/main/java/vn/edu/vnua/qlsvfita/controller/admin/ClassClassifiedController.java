package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.ClassClassifiedDTO;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.CreateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.DeleteClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.GetClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.UpdateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.service.admin.ClassClassifiedService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/class-classified")
@RequiredArgsConstructor
public class ClassClassifiedController extends BaseController {
    private final ClassClassifiedService classClassifiedService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> getClassClassifiedList(@Valid @RequestBody GetClassClassifiedRequest request){
        Page<ClassTerm> page = classClassifiedService.getClassClassified(request);
        List<ClassClassifiedDTO> response = page.getContent().stream().map(
                classTerm -> modelMapper.map(classTerm, ClassClassifiedDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> createClassClassified(@Valid @RequestBody CreateClassClassifiedRequest request){
        ClassClassifiedDTO response = modelMapper.map(classClassifiedService.createClassClassified(request), ClassClassifiedDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> updateClassClassified(@Valid @RequestBody UpdateClassClassifiedRequest request){
        ClassClassifiedDTO response = modelMapper.map(classClassifiedService.updateClassClassified(request), ClassClassifiedDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> deleteClassClassified(@Valid @RequestBody DeleteClassClassifiedRequest request){
        classClassifiedService.deleteClassClassified(request);
        String message = "Xóa thành công lớp "+request.getClassId()+" trong học kỳ " +request.getTermId();
        return buildItemResponse(message);
    }
}
