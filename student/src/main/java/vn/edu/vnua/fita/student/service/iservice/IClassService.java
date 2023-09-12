package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.table.Class;
import vn.edu.vnua.fita.student.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.classes.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.classes.UpdateClassRequest;

public interface IClassService {
    Page<Class> getClassList(GetClassListRequest request);
    Class createClass(CreateClassRequest request);
    Class updateClass(UpdateClassRequest request);
    Class deleteClass(String id);
}
