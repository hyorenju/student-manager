package vn.edu.vnua.fita.student.service.admin.management;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.table.Course;
import vn.edu.vnua.fita.student.repository.customrepo.CustomCourseRepository;
import vn.edu.vnua.fita.student.repository.jparepo.CourseRepository;
import vn.edu.vnua.fita.student.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.fita.student.request.admin.course.GetCourseListRequest;
import vn.edu.vnua.fita.student.service.iservice.ICourseService;

@Service
@RequiredArgsConstructor
public class CourseManager implements ICourseService {
    private final CourseRepository courseRepository;
    private final String courseHadExisted = "Mã khoá đã tồn tại trong hệ thống";
    private final String courseNotFound = "Mã khoá %s không tồn tại trong hệ thống";

    @Override
    public Page<Course> getCourseList(GetCourseListRequest request) {
        Specification<Course> specification = CustomCourseRepository.filterCourseList(
                request.getId()
        );
        return courseRepository.findAll(specification, PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Course createCourse(CreateCourseRequest request) {
        if(courseRepository.existsById(request.getId())){
            throw new RuntimeException(courseHadExisted);
        }
        Course course = Course.builder()
                .id(request.getId())
                .name("Khóa "+request.getId())
                .build();
        return courseRepository.saveAndFlush(course);
    }

    @Override
    public Course deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(courseNotFound, id)));
        courseRepository.deleteById(id);
        return course;
    }
}
