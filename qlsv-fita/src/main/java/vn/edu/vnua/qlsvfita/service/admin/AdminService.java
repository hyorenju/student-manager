package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.request.admin.admin.CreateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.GetAdminListRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateAdminRequest;

public interface AdminService {
    Page<Admin> getAdminList(GetAdminListRequest request);

    Admin createAdmin(CreateAdminRequest request);

    Admin updateAdmin(UpdateAdminRequest request);

    void deleteAdmin(String id);
}
