package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.table.StudentStatus;
import vn.edu.vnua.fita.student.request.admin.student_status.CreateStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.UpdateStudentStatusRequest;

import java.text.ParseException;

public interface IStudentStatusService {
    Page<StudentStatus> getStudentStatusList(GetStudentStatusListRequest request);
    StudentStatus createStudentStatus(CreateStudentStatusRequest request);
    StudentStatus updateStudentStatus(UpdateStudentStatusRequest request, Long id);
    StudentStatus deleteStudentStatus(Long id);
}
