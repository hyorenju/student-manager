package vn.edu.vnua.qlsvfita.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.UserDetailsImpl;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;

import javax.persistence.EntityManagerFactory;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        if(id.matches("^[a-zA-Z]+")) {
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy quản trị viên"));
            return UserDetailsImpl.builder()
                    .id(admin.getId())
                    .userName(admin.getName())
                    .email(admin.getEmail())
                    .password(admin.getPassword())
                    .roleId(admin.getRole().getId())
                    .avatar(admin.getAvatar())
                    .authorities(admin.getAuthorities())
                    .build();
        } else {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
            return UserDetailsImpl.builder()
                    .id(student.getId())
                    .userName(student.getName())
                    .email(student.getEmail())
                    .password(student.getPassword())
                    .roleId(student.getRole().getId())
                    .authorities(student.getAuthorities())
                    .build();
        }
    }
}
