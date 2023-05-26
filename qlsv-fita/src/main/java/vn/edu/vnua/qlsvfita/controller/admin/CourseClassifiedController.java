package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.ClassClassifiedDTO;
import vn.edu.vnua.qlsvfita.model.dto.CourseClassifiedDTO;
import vn.edu.vnua.qlsvfita.model.entity.CourseTerm;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.CreateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.DeleteClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.GetClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.UpdateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.*;
import vn.edu.vnua.qlsvfita.service.admin.CourseClassifiedService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/course-classified")
@RequiredArgsConstructor
public class CourseClassifiedController extends BaseController {
    private final CourseClassifiedService courseClassifiedService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_COURSE_CLASSIFICATION')")
    public ResponseEntity<?> getCourseClassifiedList(@Valid @RequestBody GetCourseClassifiedRequest request){
        Page<CourseTerm> page = courseClassifiedService.getCourseClassified(request);
        List<CourseClassifiedDTO> response = page.getContent().stream().map(
                courseTerm -> modelMapper.map(courseTerm, CourseClassifiedDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> createCourseClassified(@Valid @RequestBody CreateCourseClassifiedRequest request){
        CourseClassifiedDTO response = modelMapper.map(courseClassifiedService.createCourseClassified(request), CourseClassifiedDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> updateCourseClassified(@Valid @RequestBody UpdateCourseClassifiedRequest request){
        CourseClassifiedDTO response = modelMapper.map(courseClassifiedService.updateCourseClassified(request), CourseClassifiedDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_CLASS_CLASSIFICATION')")
    public ResponseEntity<?> deleteCourseClassified(@Valid @RequestBody DeleteCourseClassifiedRequest request){
        courseClassifiedService.deleteCourseClassified(request);
        String message = "Xóa thành công khóa "+request.getCourseId()+" trong học kỳ " +request.getTermId();
        return buildItemResponse(message);
    }

    @PostMapping("statistic")
    public ResponseEntity<?> statisticCourse(@Valid @RequestBody StatisticCourseRequest request){
        CourseClassifiedDTO response = modelMapper.map(courseClassifiedService.statisticCourseClassified(request), CourseClassifiedDTO.class);
        return buildItemResponse(response);
    }
}
