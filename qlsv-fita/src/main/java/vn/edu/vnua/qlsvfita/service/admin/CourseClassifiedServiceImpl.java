package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.*;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseClassifiedServiceImpl implements CourseClassifiedService {
    private final CourseClassifiedRepository courseClassifiedRepository;
    private final CourseRepository courseRepository;
    private final TermRepository termRepository;
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;

    @Override
    public Page<CourseTerm> getCourseClassified(GetCourseClassifiedRequest request) {
        List<CourseTerm> courses = courseClassifiedRepository.findAll();
        for (CourseTerm course :
                courses) {
            Course courseId = Course.builder().id(course.getCourseId()).build();
            List<Student> students = studentRepository.findAllByCourse(courseId);

            long countExcellent = 0;
            long countGood = 0;
            long countFair = 0;
            long countMedium = 0;
            long countWeak = 0;
            long countWorst = 0;
            long numOfStu = 0;

            for (Student student :
                    students) {
                if (pointRepository.existsByStudentIdAndTermId(student.getId(), course.getTermId())) {
                    StudentTerm pointCell = pointRepository.getByStudentIdAndTermId(student.getId(), course.getTermId());
                    if (pointCell.getTrainingPoint() != null) {
                        int point = pointCell.getTrainingPoint();
                        if (point > 0) {
                            numOfStu++;
                            if (point >= 90) {
                                countExcellent++;
                            } else if (point >= 80) {
                                countGood++;
                            } else if (point >= 65) {
                                countFair++;
                            } else if (point >= 50) {
                                countMedium++;
                            } else if (point >= 35) {
                                countWeak++;
                            } else {
                                countWorst++;
                            }
                        }
                    }
                }
            }
            CourseTerm anotherCourses = courseClassifiedRepository.findByCourseIdAndTermId(course.getCourseId(), course.getTermId());
            anotherCourses.setNumOfStu(numOfStu);
            anotherCourses.setExcellent(countExcellent);
            anotherCourses.setGood(countGood);
            anotherCourses.setFair(countFair);
            anotherCourses.setMedium(countMedium);
            anotherCourses.setWeak(countWeak);
            anotherCourses.setWorst(countWorst);
            courseClassifiedRepository.saveAndFlush(anotherCourses);
        }
        Specification<CourseTerm> specification = CustomCourseClassifiedRepository.filterCourseClassified(
                request.getCourseId(),
                request.getTermId()
        );
        return courseClassifiedRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public CourseTerm createCourseClassified(CreateCourseClassifiedRequest request) {
        if (courseClassifiedRepository.existsByCourseIdAndTermId(request.getCourseId(), request.getTermId())) {
            throw new RuntimeException("Dữ liệu của khoá " + request.getCourseId() + " trong học kỳ " + request.getTermId() + " đã tồn tại trong hệ thống");
        } else if (!courseRepository.existsById(request.getCourseId())) {
            throw new RuntimeException("Không tồn tại khóa " + request.getCourseId() + " trong hệ thống");
        } else if (!termRepository.existsById(request.getTermId())) {
            throw new RuntimeException("Không tồn tại học kỳ " + request.getTermId() + " trong hệ thống");
        }
        CourseTerm courseClassified = CourseTerm.builder()
                .courseId(request.getCourseId())
                .termId(request.getTermId())
                .build();
        courseClassified = courseClassifiedRepository.saveAndFlush(courseClassified);
        return courseClassified;
    }

    @Override
    public CourseTerm updateCourseClassified(UpdateCourseClassifiedRequest request) {
//        if(!courseClassifiedRepository.existsByCourseIdAndTermId(request.getCourseId(), request.getTermId())){
//            throw new RuntimeException("Dữ liệu của khóa "+request.getCourseId()+" trong học kỳ "+request.getTermId()+" không tồn tại trong hệ thống");
//        }
//        Course courseId = Course.builder().id(request.getCourseId()).build();
//        List<Student> students = studentRepository.findAllByCourseAndStatus(courseId, "Còn đi học");
//        long countNum = students.size();
//        long countExcellent = 0;
//        long countGood = 0;
//        long countFair = 0;
//        long countMedium = 0;
//        long countWeak = 0;
//        long countWorst = 0;
//        for (Student student :
//                students) {
//            if (pointRepository.getByStudentIdAndTermId(student.getId(), request.getTermId()) != null) {
//                int point = pointRepository.getByStudentIdAndTermId(student.getId(), request.getTermId()).getTrainingPoint();
//                if (point >= 90) {
//                    countExcellent++;
//                } else if (point >= 80) {
//                    countGood++;
//                } else if (point >= 65) {
//                    countFair++;
//                } else if (point >= 50) {
//                    countMedium++;
//                } else if (point >= 35) {
//                    countWeak++;
//                } else {
//                    countWorst++;
//                }
//            }
//        }
//        CourseTerm courseClassified = CourseTerm.builder()
//                .id(courseClassifiedRepository.findByCourseIdAndTermId(request.getCourseId(), request.getTermId()).getId())
//                .courseId(request.getCourseId())
//                .termId(request.getTermId())
//                .numOfStu(countNum)
//                .excellent(countExcellent)
//                .good(countGood)
//                .fair(countFair)
//                .medium(countMedium)
//                .weak(countWeak)
//                .worst(countWorst)
//                .build();
//        courseClassified = courseClassifiedRepository.saveAndFlush(courseClassified);
        return null;
    }

    @Override
    public void deleteCourseClassified(DeleteCourseClassifiedRequest request) {
        if (!courseClassifiedRepository.existsByCourseIdAndTermId(request.getCourseId(), request.getTermId())) {
            throw new RuntimeException("Dữ liệu của khóa " + request.getCourseId() + " trong học kỳ " + request.getTermId() + " không tồn tại trong hệ thống");
        }
        courseClassifiedRepository.deleteById(courseClassifiedRepository.findByCourseIdAndTermId(request.getCourseId(), request.getTermId()).getId());
    }

    @Override
    public CourseTerm statisticCourseClassified(StatisticCourseRequest request) {
        if (termRepository.existsById(request.getTermId()) && !courseRepository.existsById(request.getCourseId())) {
            throw new RuntimeException("Không tồn tại khóa " + request.getCourseId() + " trong hệ thống");
        } else if (!courseClassifiedRepository.existsByCourseIdAndTermId(request.getCourseId(), request.getTermId())) {
            throw new RuntimeException("Dữ liệu của khoá " + request.getCourseId() + " trong học kỳ " + request.getTermId() + " không tồn tại trong hệ thống");
        } else if (!termRepository.existsById(request.getTermId())) {
            throw new RuntimeException("Không tồn tại học kỳ " + request.getTermId() + " trong hệ thống");
        }
        return courseClassifiedRepository.findByCourseIdAndTermId(request.getCourseId(), request.getTermId());
    }


}
