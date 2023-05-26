package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.Term;
import vn.edu.vnua.qlsvfita.repository.ClassRepository;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.repository.CustomClassListRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.qlsvfita.request.admin.classes.GetClassListRequest;
import vn.edu.vnua.qlsvfita.request.admin.classes.UpdateClassRequest;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService{

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    @Override
    public Page<Class> getClassList(GetClassListRequest request) {
        List<Class> classes = classRepository.findAll();
        for (Class aClass:
             classes) {
            String classId = aClass.getId();

            List<Student> students = studentRepository.findAllByStatus("Còn đi học");
            long countNum = 0;
            for (Student student:
                    students) {
                if(Objects.equals(student.getClasses().getId(), classId)){
                    countNum++;
                }
            }
            Class class_ = classRepository.getById(classId);
            class_.setNumOfStu(countNum);
            classRepository.saveAndFlush(class_);

        }
        Specification<Class> specification = CustomClassListRepository.filterClassList(
                request.getId()
        );
        return classRepository.findAll(specification, PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Class createClass(CreateClassRequest request) {
        if (classRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã lớp đã tồn tại trong hệ thống");
        } else {
            Class aClass = Class.builder()
                    .id(request.getId())
                    .className(request.getName())
                    .build();

            aClass = classRepository.saveAndFlush(aClass);
            return aClass;
        }
    }

    @Override
    public Class updateClass(UpdateClassRequest request, String id) {
        if (!classRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại mã lớp " + id + " trong hệ thống");
        }
//        List<Student> students = studentRepository.findAllByStatus("Còn đi học");
//        long countNum = 0;
//        for (Student student:
//             students) {
//            if(Objects.equals(student.getClasses().getId(), id)){
//                countNum++;
//            }
//        }
        Class aClass = classRepository.getById(id);
        aClass.setClassName(request.getName());
        aClass = classRepository.saveAndFlush(aClass);
        return aClass;
    }

    @Override
    public void deleteClass(String id) {
        if(!classRepository.existsById(id)){
            throw new RuntimeException("Mã lớp "+id+" không tồn tại trong hệ thống");
        }
        classRepository.deleteById(id);
    }
}
