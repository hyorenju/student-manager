package vn.edu.vnua.qlsvfita.service.student;

import vn.edu.vnua.qlsvfita.model.dto.StudentAccumulationDTO;
import vn.edu.vnua.qlsvfita.model.dto.StudentDetailDTO;
import vn.edu.vnua.qlsvfita.model.dto.StudentListDTO;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.ChangePasswordRequest;
import vn.edu.vnua.qlsvfita.request.student.GetStudentDetailRequest;
import vn.edu.vnua.qlsvfita.request.student.UpdateStudentDetailRequest;

import java.text.ParseException;
import java.util.List;

public interface DetailService {
    StudentDetailDTO getStudentDetail(String id);

    List<StudentTerm> getStudentPoint(String id);

    StudentAccumulationDTO getStudentAccumulation(String id);

    Student updateStudentDetail(UpdateStudentDetailRequest request) throws ParseException;

    void changePassword(ChangePasswordRequest request);
}
