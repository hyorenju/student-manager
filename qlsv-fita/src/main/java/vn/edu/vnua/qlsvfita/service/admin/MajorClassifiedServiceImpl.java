package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.CreateCourseClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.DeleteCourseClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.GetCourseClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.course.UpdateCourseClassifiedRequest;

@Service
@RequiredArgsConstructor
public class MajorClassifiedServiceImpl implements MajorClassifiedService{
    private final ClassClassifiedRepository classClassifiedRepository;
    private final ClassRepository classRepository;
    private final TermRepository termRepository;
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;

    @Override
    public Page<ClassTerm> getMajorClassified(GetCourseClassifiedRequest request) {
        return null;
    }

    @Override
    public ClassTerm createMajorClassified(CreateCourseClassifiedRequest request) {
        return null;
    }

    @Override
    public ClassTerm updateMajorClassified(UpdateCourseClassifiedRequest request) {
        return null;
    }

    @Override
    public void deleteMajorClassified(DeleteCourseClassifiedRequest request) {

    }
}
