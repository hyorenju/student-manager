package vn.edu.vnua.qlsvfita.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.UserDetailsImpl;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.response.LoginResponse;
import vn.edu.vnua.qlsvfita.security.token.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtUtils;
    private final PasswordEncoder encoder;

    @Override
    public LoginResponse authenticateUser(String id, String password) {
        if (studentRepository.existsById(id)) {
            if(!encoder.matches(password, studentRepository.getStudentById(id).getPassword())){
                throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
            }
        } else if (adminRepository.existsById(id)) {
            if(!encoder.matches(password, adminRepository.getById(id).getPassword())){
                throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
            }
        } else {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getRoleId(),
                userDetails.getAvatar());
    }
}
