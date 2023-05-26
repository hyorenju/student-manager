package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.request.ChangePasswordRequest;
import vn.edu.vnua.qlsvfita.service.admin.PasswordService;

import javax.validation.Valid;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class ChangePasswordController extends BaseController {
    private final PasswordService passwordService;

    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        passwordService.changePassword(request);
        String message = "Đổi mật khẩu thành công";
        return buildItemResponse(message);
    }



}
