package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.model.entity.CourseTerm;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.CreateCourseClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.DeleteCourseClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.GetCourseClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.UpdateCourseClassifiedRequest;

public interface MajorClassifiedService {
    Page<ClassTerm> getMajorClassified(GetCourseClassifiedRequest request);

    ClassTerm createMajorClassified(CreateCourseClassifiedRequest request);

    ClassTerm updateMajorClassified(UpdateCourseClassifiedRequest request);

    void deleteMajorClassified(DeleteCourseClassifiedRequest request);
}
