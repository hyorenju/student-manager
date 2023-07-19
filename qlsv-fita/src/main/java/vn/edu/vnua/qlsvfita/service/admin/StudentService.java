package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.model.dto.*;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.admin.student.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface StudentService {
    List<StudentListDTOTest> getStudenList();

    Page<Student> filterStudentList(GetStudentListRequest request);
    Student getStudentById(String id);
    StudentAccumulationDTO getAccumulation(String id);
    StudentDetailDTO createStudent(CreateStudentRequest request) throws ParseException;
    StudentDetailDTO updateStudent(UpdateStudentRequest request, String id) throws ParseException;
    void deleteStudentById(List<String> id);
    void deleteAllStudentById(DeleteStudentRequest ids);
    List<StudentExportDTO> exportToExcel(ExportStudentListRequest request);
    void importToDatabase(MultipartFile file) throws IOException, ParseException;

//    void importFileToDatabase(MultipartFile file) throws IOException, ParseException;
}
