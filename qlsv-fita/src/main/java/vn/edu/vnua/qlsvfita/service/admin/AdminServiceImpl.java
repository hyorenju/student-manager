package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.model.entity.Role;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.repository.CustomAdminListRepository;
import vn.edu.vnua.qlsvfita.repository.CustomStudentListRepository;
import vn.edu.vnua.qlsvfita.repository.RoleRepository;
import vn.edu.vnua.qlsvfita.request.admin.admin.CreateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.GetAdminListRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateAdminRequest;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    @Override
    public Page<Admin> getAdminList(GetAdminListRequest request) {
//        return adminRepository.getAllByRoleIsNot(
//                Role.builder().id("SUPERADMIN").build(),
//                PageRequest.of(request.getPage()-1, request.getSize()));
        Specification<Admin> specification = CustomAdminListRepository.filterAdminList(
                request.getId()
        );
        return adminRepository.findAll(specification, PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Admin createAdmin(CreateAdminRequest request) {
        if (adminRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã quản trị viên đã tồn tại trong hệ thống");
        }
        if(Objects.equals(request.getRoleId(), "SUPERADMIN")){
            throw new RuntimeException("Không thể cấp quyền SUPER ADMIN");
        }
        if(adminRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }
        if(!roleRepository.existsById(request.getRoleId())){
            throw new RuntimeException("Mã quyền không tồn tại trong hệ thống");
        }
        Admin admin = Admin.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.builder().id(request.getRoleId()).build())
                .build();
        admin = adminRepository.saveAndFlush(admin);
        return admin;
    }

    @Override
    public Admin updateAdmin(UpdateAdminRequest request) {
        if (!adminRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã quản trị viên không tồn tại trong hệ thống");
        }
        if(Objects.equals(request.getRoleId(), "SUPERADMIN")){
            throw new RuntimeException("Không thể cấp quyền SUPER ADMIN cho người này");
        }
        if(!roleRepository.existsById(request.getRoleId())){
            throw new RuntimeException("Mã quyền không tồn tại trong hệ thống");
        }

        Admin admin = adminRepository.getById(request.getId());
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setRole(Role.builder().id(request.getRoleId()).build());
        if (request.getPassword() == null) {
            admin.setPassword(encoder.encode(admin.getPassword()));
        } else {
            admin.setPassword(encoder.encode(request.getPassword()));
        }

        return admin;
    }

    @Override
    public void deleteAdmin(String id) {
        if(!adminRepository.existsById(id)){
            throw new RuntimeException("Mã quản trị viên không tồn tại trong hệ thống");
        }
        if(Objects.equals(adminRepository.getById(id).getRole().getId(), "SUPERADMIN")){
            throw new RuntimeException("Không thể xóa SUPER ADMIN");
        }
        adminRepository.deleteById(id);
    }
}
