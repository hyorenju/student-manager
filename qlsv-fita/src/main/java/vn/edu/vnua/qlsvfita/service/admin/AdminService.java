package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.request.admin.admin.CreateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.GetAdminListRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateMyselfRequest;

public interface AdminService {
    Page<Admin> getAdminList(GetAdminListRequest request);

    Admin getAdminDetail(String id);

    Admin createAdmin(CreateAdminRequest request);

    Admin updateAdmin(UpdateAdminRequest request);

    Admin updateMyself(UpdateMyselfRequest request);

    Admin updateAvatar(String id, MultipartFile multipartFile);

    void deleteAdmin(String id);
}
