package vn.edu.vnua.fita.student.controller.admin.management;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.dto.AdminDTO;
import vn.edu.vnua.fita.student.entity.dto.StudentDTO;
import vn.edu.vnua.fita.student.entity.dto.TrashStudentDTO;
import vn.edu.vnua.fita.student.entity.manager.trash.TrashStudent;
import vn.edu.vnua.fita.student.entity.table.Student;
import vn.edu.vnua.fita.student.request.admin.student.*;
import vn.edu.vnua.fita.student.service.admin.management.StudentManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("admin/student")
@RequiredArgsConstructor
public class StudentController extends BaseController {
    private final StudentManager studentManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getStudentList(@Valid @RequestBody GetStudentListRequest request) {
        Page<Student> page = studentManager.getStudentList(request);
        List<StudentDTO> response = page.getContent().stream().map(
                student -> modelMapper.map(student, StudentDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> getStudentDetail(@PathVariable String id) {
        StudentDTO response = modelMapper.map(studentManager.getStudentById(id), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createStudent(@Valid @RequestBody CreateStudentRequest request){
        StudentDTO response = modelMapper.map(studentManager.createStudent(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateStudent(@Valid @RequestBody UpdateStudentRequest request){
        StudentDTO response = modelMapper.map(studentManager.updateStudent(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id){
        TrashStudentDTO response = modelMapper.map(studentManager.deleteStudent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteManyStudent(@RequestBody @Valid DeleteStudentRequest request){
        List<TrashStudentDTO> response = studentManager.deleteManyStudent(request).stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("restore/{id}")
    public ResponseEntity<?> restoreStudent(@PathVariable Long id){
        TrashStudentDTO response = modelMapper.map(studentManager.restoreStudent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore")
    public ResponseEntity<?> restoreManyStudent(@RequestBody @Valid RestoreStudentRequest request){
        List<TrashStudentDTO> response = studentManager.restoreManyStudent(request).stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("trash")
    public ResponseEntity<?> getTrashStudent(@Valid @RequestBody GetTrashStudentRequest request){
        Page<TrashStudent> page = studentManager.getTrashStudentList(request);
        List<TrashStudentDTO> response = page.getContent().stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("avatar/{id}")
    public ResponseEntity<?> updateAvatar(@RequestBody MultipartFile file, @PathVariable String id) throws IOException {
        StudentDTO response = modelMapper.map(studentManager.updateAvatar(file, id), StudentDTO.class);
        return buildItemResponse(response);
    }
}
