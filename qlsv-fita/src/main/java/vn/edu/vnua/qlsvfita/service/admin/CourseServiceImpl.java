package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Course;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.repository.CourseRepository;
import vn.edu.vnua.qlsvfita.repository.CustomClassListRepository;
import vn.edu.vnua.qlsvfita.repository.CustomCourseListRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.qlsvfita.request.admin.course.GetCourseListRequest;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    public Page<Course> getCourseList(GetCourseListRequest request) {
        List<Course> courses = courseRepository.findAll();
        for (Course course:
                courses) {
            String courseId = course.getId();

            List<Student> students = studentRepository.findAllByStatus("Còn đi học");
            long countNum = 0;
            for (Student student:
                    students) {
                if(Objects.equals(student.getCourse().getId(), courseId)){
                    countNum++;
                }
            }
            Course anotherCourse = courseRepository.getById(courseId);
            anotherCourse.setNumOfStu(countNum);
            courseRepository.saveAndFlush(anotherCourse);
        }
        Specification<Course> specification = CustomCourseListRepository.filterCourseList(
                request.getId()
        );
        return courseRepository.findAll(specification, PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Course createCourse(CreateCourseRequest request) {
        if(courseRepository.existsById(request.getId())){
            throw new RuntimeException("Mã khóa đã tồn tại trong hệ thống");
        }
        Course course = Course.builder()
                .id(request.getId())
                .name("Khóa "+request.getId().split("K")[1])
                .build();
        course = courseRepository.saveAndFlush(course);
        return course;
    }

    @Override
    public Course updateCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại mã khóa " + id + " trong hệ thống");
        }
//        List<Student> students = studentRepository.findAllByStatus("Còn đi học");
//        long countNum = 0;
//        for (Student student:
//                students) {
//            if(Objects.equals(student.getClasses().getId().split("\\D+$")[0], id)){
//                countNum++;
//            }
//        }
//        Course course = Course.builder()
//                .id(id)
//                .name("Khóa "+id.split("K")[1])
//                .numOfStu(countNum)
//                .build();
//        course = courseRepository.saveAndFlush(course);
//        return course;
        Course course = courseRepository.getById(id);
        course.setName("Khóa "+id.split("K")[1]);
        course = courseRepository.saveAndFlush(course);
        return course;
    }

    @Override
    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại mã khóa " + id + " trong hệ thống");
        }
        courseRepository.deleteById(id);
    }
}
