package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.CourseListDTO;
import vn.edu.vnua.qlsvfita.model.entity.Course;
import vn.edu.vnua.qlsvfita.model.entity.CourseTerm;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.StatisticCourseRequest;
import vn.edu.vnua.qlsvfita.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.qlsvfita.request.admin.course.GetCourseListRequest;
import vn.edu.vnua.qlsvfita.service.admin.CourseService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/course")
@RequiredArgsConstructor
public class CourseController extends BaseController {
    private final CourseService courseService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_COURSE_LIST')")
    public ResponseEntity<?> getCourseList(@RequestBody GetCourseListRequest request){
        Page<Course> page = courseService.getCourseList(request);
        List<CourseListDTO> response = page.getContent().stream().map(
                course -> modelMapper.map(course, CourseListDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_COURSE')")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CreateCourseRequest request){
        CourseListDTO response = modelMapper.map(courseService.createCourse(request), CourseListDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_COURSE')")
    public ResponseEntity<?> updateCourse(@PathVariable String id){
        CourseListDTO response = modelMapper.map(courseService.updateCourse(id), CourseListDTO.class);
        return buildItemResponse(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_COURSE')")
    public ResponseEntity<?> deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
        String message = "Xóa thành công mã khóa "+id;
        return buildItemResponse(message);
    }

}
