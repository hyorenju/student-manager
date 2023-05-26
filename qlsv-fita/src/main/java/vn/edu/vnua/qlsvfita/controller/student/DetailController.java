package vn.edu.vnua.qlsvfita.controller.student;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.*;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.ChangePasswordRequest;
import vn.edu.vnua.qlsvfita.request.student.GetStudentDetailRequest;
import vn.edu.vnua.qlsvfita.request.student.UpdateStudentDetailRequest;
import vn.edu.vnua.qlsvfita.service.student.DetailService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
@RequiredArgsConstructor
public class DetailController extends BaseController {
    private final DetailService detailService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("detail/{id}")
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_DETAIL')")
    public ResponseEntity<?> getDetail(@PathVariable String id){
        StudentDetailDTO response = detailService.getStudentDetail(id);
        return buildItemResponse(response);
    }

    @GetMapping("point/{id}")
    @PreAuthorize("hasAnyAuthority('GET_POINT_DETAIL')")
    public ResponseEntity<?> getPoint(@PathVariable String id){
        List<StudentPointDTO> response = detailService.getStudentPoint(id).stream().map(
                studentTerm -> modelMapper.map(studentTerm, StudentPointDTO.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @GetMapping("accumulation/{id}")
    @PreAuthorize("hasAnyAuthority('GET_ACCUMULATION_DETAIL')")
    public ResponseEntity<?> getAccumulation(@PathVariable String id){
        StudentAccumulationDTO response = detailService.getStudentAccumulation(id);
        return buildItemResponse(response);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_STUDENT_DETAIL')")
    public ResponseEntity<?> updateDetail(@Valid @RequestBody UpdateStudentDetailRequest request) throws ParseException {
        StudentDetailDTO response = modelMapper.map(detailService.updateStudentDetail(request), StudentDetailDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("change-password")
    @PreAuthorize("hasAnyAuthority('CHANGE_PASSWORD')")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        detailService.changePassword(request);
        String message = "Đổi mật khẩu thành công";
        return buildItemResponse(message);
    }

    @GetMapping("study-process-10/{id}")
    public ResponseEntity<?> studyProcess10(@PathVariable String id){
        List<StudentTerm> list = detailService.getStudentPoint(id);
        List<StudyProcess10> response = list.stream().map(
                studentTerm -> modelMapper.map(studentTerm, StudyProcess10.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @GetMapping("study-process-4/{id}")
    public ResponseEntity<?> studyProcess4(@PathVariable String id){
        List<StudentTerm> list = detailService.getStudentPoint(id);
        List<StudyProcess4> response = list.stream().map(
                studentTerm -> modelMapper.map(studentTerm, StudyProcess4.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }

    @GetMapping("training-process/{id}")
    public ResponseEntity<?> trainingProcess(@PathVariable String id){
        List<StudentTerm> list = detailService.getStudentPoint(id);
        List<TrainingProcess> response = list.stream().map(
                studentTerm -> modelMapper.map(studentTerm, TrainingProcess.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(response, response.size());
    }
}
