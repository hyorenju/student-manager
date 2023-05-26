package vn.edu.vnua.qlsvfita.service.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.qlsvfita.request.admin.classes.GetClassListRequest;
import vn.edu.vnua.qlsvfita.request.admin.classes.UpdateClassRequest;

public interface ClassService {
    Page<Class> getClassList(GetClassListRequest request);

    Class createClass(CreateClassRequest request);

    Class updateClass(UpdateClassRequest request, String classId);

    void deleteClass(String id);
}
