package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.FileInfo;
import vn.edu.vnua.qlsvfita.model.dto.*;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.admin.student.*;
import vn.edu.vnua.qlsvfita.service.admin.FilesStorageService;
import vn.edu.vnua.qlsvfita.service.admin.StatisticsService;
import vn.edu.vnua.qlsvfita.service.admin.StudentService;
import vn.edu.vnua.qlsvfita.util.MyUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/student")
@RequiredArgsConstructor
public class StudentController extends BaseController {
    private final StudentService studentService;
    private final StatisticsService statisticsService;
    private final FilesStorageService storageService;
    private final ModelMapper modelMapper = new ModelMapper();


    @GetMapping
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_LIST')")
    public ResponseEntity<?> getList(){
        List<StudentListDTOTest> response = studentService.getStudenList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_LIST')")
    public ResponseEntity<?> getStudentList(@Valid @RequestBody GetStudentListRequest request) {
        Page<Student> page = studentService.filterStudentList(request);
        List<Student> students = page.getContent();
        List<StudentListDTO> response = new ArrayList<>();
        for (Student student:
             students) {
            StudentListDTO studentListDTO = modelMapper.map(student, StudentListDTO.class);
            studentListDTO.setDob(MyUtils.convertStringFromTimestamp(student.getDob()));
//            studentListDTO
            if(student.getStatusDate()!=null){
                studentListDTO.setStatusDate(MyUtils.convertStringFromTimestamp(student.getStatusDate()));
            }
            response.add(studentListDTO);
        }

//        List<StudentListDTO> response = page.getContent().stream().map(
//                student -> modelMapper.map(student, StudentListDTO.class)
//        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @GetMapping("detail/{id}")
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_DETAIL')")
    public ResponseEntity<?> getStudentDetail(@PathVariable String id) {
        StudentDetailDTO response = modelMapper.map(studentService.getStudentById(id),StudentDetailDTO.class);
        return buildItemResponse(response);
    }

    @GetMapping("accumulation/{id}")
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_DETAIL')")
    public ResponseEntity<?> getAccumulation(@PathVariable String id){
        StudentAccumulationDTO response = studentService.getAccumulation(id);
        return buildItemResponse(response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_STUDENT')")
    public ResponseEntity<?> createNewStudent(@Valid @RequestBody CreateStudentRequest request) throws ParseException {
        StudentDetailDTO response = studentService.createStudent(request);
        return buildItemResponse(response);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_STUDENT')")
    public ResponseEntity<?> updateStudent(@Valid @RequestBody UpdateStudentRequest request, @PathVariable String id) throws ParseException {
        StudentDetailDTO response = studentService.updateStudent(request, id);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_STUDENT')")
    public ResponseEntity<?> deleteSelectedStudent(@RequestBody DeleteStudentRequest request){
        studentService.deleteAllStudentById(request);
        int size = request.getIds().size();
        String message = "Xóa thành công "+ size +" sinh viên có mã: ";
        for(int i = 0; i < size-1; i++){
            message = message+request.getIds().get(i)+"; ";
        }
        message = message+request.getIds().get(size-1);
        return buildItemResponse(message);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_STUDENT')")
    public ResponseEntity<?> deleteStudent(@PathVariable String id){
        studentService.deleteStudentById(id);
        String message = "Xóa thành công sinh viên "+id;
        return buildItemResponse(message);
    }

    @GetMapping("study-process-10/{id}")
    public ResponseEntity<?> studyProcess10(@PathVariable String id){
        List<StudentTerm> list = statisticsService.StudyProcess(id);
        List<StudyProcess10> response = list.stream().map(
                studentTerm -> modelMapper.map(studentTerm, StudyProcess10.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @GetMapping("study-process-4/{id}")
    public ResponseEntity<?> studyProcess4(@PathVariable String id){
        List<StudentTerm> list = statisticsService.StudyProcess(id);
        List<StudyProcess4> response = list.stream().map(
                studentTerm -> modelMapper.map(studentTerm, StudyProcess4.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @GetMapping("training-process/{id}")
    public ResponseEntity<?> trainingProcess(@PathVariable String id){
        List<StudentTerm> list = statisticsService.StudyProcess(id);
        List<TrainingProcess> response = list.stream().map(
                studentTerm -> modelMapper.map(studentTerm, TrainingProcess.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("export")
    public ResponseEntity<?> download(@Valid @RequestBody ExportStudentListRequest request) {
        List<StudentExportDTO> response = studentService.exportToExcel(request);
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('IMPORT_STUDENT_LIST')")
    public ResponseEntity<?> upload(MultipartFile file) throws IOException, ParseException {
        studentService.importToDatabase(file);
        return buildItemResponse("Nhập dữ liệu thành công");
    }

//    @PostMapping("import/student-info")
//    public ResponseEntity<?> importToDatabase(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
//        studentService.importFileToDatabase(file);
//        return buildItemResponse("Nhập dữ liệu thành công");
//    }
}
