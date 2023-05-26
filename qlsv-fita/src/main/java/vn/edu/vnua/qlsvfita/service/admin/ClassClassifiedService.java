package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.ClassTerm;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.CreateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.DeleteClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.GetClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.UpdateClassClassifiedRequest;

import java.util.List;

public interface ClassClassifiedService {
    Page<ClassTerm> getClassClassified(GetClassClassifiedRequest request);

    ClassTerm createClassClassified(CreateClassClassifiedRequest request);

    ClassTerm updateClassClassified(UpdateClassClassifiedRequest request);

    void deleteClassClassified(DeleteClassClassifiedRequest request);

}
