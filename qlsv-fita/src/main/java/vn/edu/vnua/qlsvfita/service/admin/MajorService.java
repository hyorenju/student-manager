package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.GetMajorListRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.UpdateMajorRequest;

public interface MajorService {
    Page<Major> getMajorList(GetMajorListRequest request);

    Major createMajor(CreateMajorRequest request);

    Major updateMajor(UpdateMajorRequest request, String id);

    void deleteMajor(String id);
}
