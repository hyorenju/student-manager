package vn.edu.vnua.fita.student.service.admin.management;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.table.Class;
import vn.edu.vnua.fita.student.repository.customrepo.CustomClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.classes.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.classes.UpdateClassRequest;
import vn.edu.vnua.fita.student.service.iservice.IClassService;

@Service
@RequiredArgsConstructor
public class ClassManager implements IClassService {
    private final ClassRepository classRepository;
    private final String classHadExisted = "Mã lớp đã tồn tại trong hệ thống";
    private final String classNotFound = "Mã lớp %s không tồn tại trong hệ thống";



    @Override
    public Page<Class> getClassList(GetClassListRequest request) {
        Specification<Class> specification = CustomClassRepository.filterClassList(
                request.getId()
        );
        return classRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Class createClass(CreateClassRequest request) {
        if(classRepository.existsById(request.getId())){
            throw new RuntimeException(classHadExisted);
        }
        Class aClass = Class.builder()
                .id(request.getId())
                .name(request.getName())
                .build();

        return classRepository.saveAndFlush(aClass);
    }

    @Override
    public Class updateClass(UpdateClassRequest request) {
        Class aClass = classRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getId())));
        aClass.setName(request.getName());
        return classRepository.saveAndFlush(aClass);
    }

    @Override
    public Class deleteClass(String id) {
        Class aClass = classRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(classNotFound, id)));
        classRepository.deleteById(id);
        return aClass;
    }
}
