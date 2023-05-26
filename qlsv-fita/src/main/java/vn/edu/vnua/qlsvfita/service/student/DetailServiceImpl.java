package vn.edu.vnua.qlsvfita.service.student;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.dto.StudentAccumulationDTO;
import vn.edu.vnua.qlsvfita.model.dto.StudentDetailDTO;
import vn.edu.vnua.qlsvfita.model.dto.StudentListDTO;
import vn.edu.vnua.qlsvfita.model.entity.*;
import vn.edu.vnua.qlsvfita.repository.MajorRepository;
import vn.edu.vnua.qlsvfita.repository.PointRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.request.ChangePasswordRequest;
import vn.edu.vnua.qlsvfita.request.student.GetStudentDetailRequest;
import vn.edu.vnua.qlsvfita.request.student.UpdateStudentDetailRequest;
import vn.edu.vnua.qlsvfita.util.MyUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public StudentDetailDTO getStudentDetail(String id) {
        Student student = studentRepository.getStudentById(id);
        StudentDetailDTO studentDTO = modelMapper.map(student, StudentDetailDTO.class);
        studentDTO.setDob(MyUtils.convertStringFromTimestamp(student.getDob()));
        if(student.getStatusDate()!=null){
            studentDTO.setStatusDate(MyUtils.convertStringFromTimestamp(student.getStatusDate()));
        }
        return studentDTO;
    }

    @Override
    public List<StudentTerm> getStudentPoint(String id) {
        return pointRepository.findAllByStudentId(id);
    }

    @Override
    public StudentAccumulationDTO getStudentAccumulation(String id) {
        List<StudentTerm> studentTerms = pointRepository.findAllByStudentId(id);

        StudentAccumulationDTO studentAccumulationDTO = new StudentAccumulationDTO();
        studentAccumulationDTO.setTotalCredits(majorRepository.getById(studentRepository.getStudentById(id).getMajor().getId()).getTotalCredits());
        if(studentTerms.size()==0){
            studentAccumulationDTO.setCreditsAccumulated(0);
            studentAccumulationDTO.setScoreAccumulated10((float) 0);
            studentAccumulationDTO.setScoreAccumulated4((float) 0);
            return studentAccumulationDTO;
        }
        int max = Integer.parseInt(studentTerms.get(0).getTermId());
        for (StudentTerm point:
                studentTerms) {
            if(Integer.parseInt(point.getTermId())>max){
                max = Integer.parseInt(point.getTermId());
            }
        }
        studentAccumulationDTO.setCreditsAccumulated(pointRepository.getByStudentIdAndTermId(id, Integer.toString(max)).getCreditsAccumulated());
        studentAccumulationDTO.setScoreAccumulated10(pointRepository.getByStudentIdAndTermId(id, Integer.toString(max)).getScoreAccumulated10());
        studentAccumulationDTO.setScoreAccumulated4(pointRepository.getByStudentIdAndTermId(id, Integer.toString(max)).getScoreAccumulated4());
        return studentAccumulationDTO;
    }

    @Override
    public Student updateStudentDetail(UpdateStudentDetailRequest request) throws ParseException {
        if(!studentRepository.existsById(request.getId())){
            throw new RuntimeException("Mã sinh viên không tồn tại trong hệ thống");
        }
        Student student = studentRepository.getStudentById(request.getId());
        student.setStatus(request.getStatus());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setEmail(request.getEmail());
        student.setHomeTown(request.getHomeTown());
        student.setResidence(request.getResidence());
        student.setFatherName(request.getFatherName());
        student.setFatherPhoneNumber(request.getFatherPhoneNumber());
        student.setMotherName(request.getMotherName());
        student.setMotherPhoneNumber(request.getMotherPhoneNumber());

        if (request.getStatusDate() != null) {
            student.setStatusDate(MyUtils.convertTimestampFromString(request.getStatusDate()));
        }

        student = studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        if(!studentRepository.existsById(request.getId())){
            throw new RuntimeException("Sinh viên không tồn tại");
        }
        Student student = studentRepository.getStudentById(request.getId());
        if(!encoder.matches(request.getValues().getCurrentPassword(), student.getPassword())){
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }
        if(encoder.matches(request.getValues().getNewPassword(), student.getPassword())){
            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }
        if(!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())){
            throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
        }
        student.setPassword(encoder.encode(request.getValues().getNewPassword()));
        studentRepository.saveAndFlush(student);
    }
}
