package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.request.ChangePasswordRequest;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService{
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;

    @Override
    public void changePassword(ChangePasswordRequest request) {
        if(!adminRepository.existsById(request.getId())){
            throw new RuntimeException("Admin không tồn tại");
        }
        Admin admin = adminRepository.getById(request.getId());
        if(!encoder.matches(request.getValues().getCurrentPassword(), admin.getPassword())){
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }
        if(encoder.matches(request.getValues().getNewPassword(), admin.getPassword())){
            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }
        if(!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())){
            throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
        }
        admin.setPassword(encoder.encode(request.getValues().getNewPassword()));
        adminRepository.saveAndFlush(admin);
    }



}
