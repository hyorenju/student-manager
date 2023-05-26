package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.model.entity.CourseTerm;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.CreateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.DeleteClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.GetClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.UpdateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.*;

public interface CourseClassifiedService {
    Page<CourseTerm> getCourseClassified(GetCourseClassifiedRequest request);

    CourseTerm createCourseClassified(CreateCourseClassifiedRequest request);

    CourseTerm updateCourseClassified(UpdateCourseClassifiedRequest request);

    void deleteCourseClassified(DeleteCourseClassifiedRequest request);

    CourseTerm statisticCourseClassified(StatisticCourseRequest request);
}
