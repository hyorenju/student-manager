package vn.edu.vnua.qlsvfita.service.admin;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.model.entity.Role;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.repository.CustomAdminListRepository;
import vn.edu.vnua.qlsvfita.repository.RoleRepository;
import vn.edu.vnua.qlsvfita.request.admin.admin.CreateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.GetAdminListRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateAdminRequest;
import vn.edu.vnua.qlsvfita.request.admin.admin.UpdateMyselfRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
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
        return adminRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Admin getAdminDetail(String id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Quản trị viên không tồn tại");
        }
        return adminRepository.getById(id);
    }

    @Override
    public Admin createAdmin(CreateAdminRequest request) {
        if (adminRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã quản trị viên đã tồn tại trong hệ thống");
        }
        if (Objects.equals(request.getRoleId(), "SUPERADMIN")) {
            throw new RuntimeException("Không thể cấp quyền SUPER ADMIN");
        }
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }
        if (!roleRepository.existsById(request.getRoleId())) {
            throw new RuntimeException("Mã quyền không tồn tại trong hệ thống");
        }
        if(request.getId().matches("^[0-9]+")){
            throw new RuntimeException("Mã quản trị viên phải bắt đầu bằng chữ cái");
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
        if (Objects.equals(request.getRoleId(), "SUPERADMIN")) {
            throw new RuntimeException("Không thể cấp quyền SUPER ADMIN cho người này");
        }
        if (!roleRepository.existsById(request.getRoleId())) {
            throw new RuntimeException("Mã quyền không tồn tại trong hệ thống");
        }

        Admin admin = adminRepository.getById(request.getId());
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setRole(Role.builder().id(request.getRoleId()).build());
        if (request.getPassword() != null) {
            admin.setPassword(encoder.encode(request.getPassword()));
        }

        return admin;
    }

    @Override
    public Admin updateMyself(UpdateMyselfRequest request) {
        if (!adminRepository.existsById(request.getId())) {
            throw new RuntimeException("Quản trị viên không tồn tại");
        }
        Admin admin = adminRepository.getById(request.getId());
        admin.setName(request.getInfo().getName());
        admin.setEmail(request.getInfo().getEmail());
        adminRepository.saveAndFlush(admin);
        return admin;
    }

    @Override
    public Admin updateAvatar(String id, MultipartFile multipartFile) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Quản trị viên không tồn tại");
        }
        Admin admin = adminRepository.getById(id);

        try {
            String fileName = multipartFile.getOriginalFilename(); // to get original file name
            fileName = UUID.randomUUID().toString().concat(
                    fileName.substring(fileName.lastIndexOf("."))
            );  // to generated random string values for file name.

            File tempFile = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(multipartFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File file = tempFile; // to convert multipartFile to File

            BlobId blobId = BlobId.of("student-4a760.appspot.com", fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("D:\\Boku no\\InternshipProject\\Project\\qlsv-fita\\src\\main\\resources\\serviceAccountKey.json"));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
            String TEMP_URL = String.format("https://firebasestorage.googleapis.com/v0/b/student-4a760.appspot.com/o/%s?alt=media", URLEncoder.encode(fileName, StandardCharsets.UTF_8)); // to get uploaded file link
            file.delete(); // to delete the copy of uploaded file stored in the project folder

            if(StringUtils.hasText(admin.getAvatar())) {
                //Xóa file
                storage.delete(BlobId.of("student-4a760.appspot.com", admin.getAvatar()));
            }

            admin.setAvatar(TEMP_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adminRepository.saveAndFlush(admin);
        return admin;
    }

    @Override
    public void deleteAdmin(String id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Mã quản trị viên không tồn tại trong hệ thống");
        }
        if (Objects.equals(adminRepository.getById(id).getRole().getId(), "SUPERADMIN")) {
            throw new RuntimeException("Không thể xóa SUPER ADMIN");
        }
        adminRepository.deleteById(id);
    }
}
