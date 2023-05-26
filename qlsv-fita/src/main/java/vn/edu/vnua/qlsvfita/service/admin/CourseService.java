package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.Course;
import vn.edu.vnua.qlsvfita.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.qlsvfita.request.admin.course.GetCourseListRequest;

public interface CourseService {
    Page<Course> getCourseList(GetCourseListRequest request);

    Course createCourse(CreateCourseRequest request);

    Course updateCourse(String id);

    void deleteCourse(String id);

}
