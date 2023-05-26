package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.repository.CustomClassListRepository;
import vn.edu.vnua.qlsvfita.repository.MajorRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.GetMajorListRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.UpdateMajorRequest;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService{
    private final MajorRepository majorRepository;
    private final StudentRepository studentRepository;

    @Override
    public Page<Major> getMajorList(GetMajorListRequest request) {
        List<Major> majors = majorRepository.findAll();
        for (Major major:
                majors) {
            String majorId = major.getId();

            List<Student> students = studentRepository.findAllByStatus("Còn đi học");
            long countNum = 0;
            for (Student student:
                    students) {
                if(Objects.equals(student.getMajor().getId(), majorId)){
                    countNum++;
                }
            }
            Major anotherMajor = majorRepository.getById(majorId);
            anotherMajor.setNumOfStu(countNum);
            majorRepository.saveAndFlush(anotherMajor);
        }
        return majorRepository.findAll(PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Major createMajor(CreateMajorRequest request) {
        if(majorRepository.existsById(request.getId())){
            throw new RuntimeException("Mã chuyên ngành đã tồn tại trong hệ thống");
        }
        Major major = Major.builder()
                .id(request.getId())
                .name(request.getName())
                .totalCredits(request.getTotalCredits())
                .build();
        major = majorRepository.saveAndFlush(major);
        return major;
    }

    @Override
    public Major updateMajor(UpdateMajorRequest request, String id) {
        if(!majorRepository.existsById(id)){
            throw new RuntimeException("Mã chuyên ngành không tồn tại trong hệ thống");
        }
//        List<Student> students = studentRepository.findAllByStatus("Còn đi học");
//        long countNum = 0;
//        for (Student student:
//             students) {
//            if(Objects.equals(student.getMajor().getId(), id)){
//                countNum++;
//            }
//        }
        Major major = majorRepository.getById(id);
        major.setName(request.getName());
        major.setTotalCredits(request.getTotalCredits());
        major = majorRepository.saveAndFlush(major);
        return major;
    }

    @Override
    public void deleteMajor(String id) {
        if(!majorRepository.existsById(id)){
            throw new RuntimeException("Mã chuyên ngành không tồn tại trong hệ thống");
        }
        majorRepository.deleteById(id);
    }
}
