package vn.edu.vnua.qlsvfita.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.request.client.ForgotPasswordRequest;
import vn.edu.vnua.qlsvfita.request.client.SendMailRequest;
import vn.edu.vnua.qlsvfita.service.client.ForgotPasswordService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class ForgotPasswordController extends BaseController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("send-request")
    public ResponseEntity<?> sendRequest(@Valid @RequestBody SendMailRequest request){
        forgotPasswordService.sendMessage(request);
        String message = "Yêu cầu đổi mật khẩu đã được gửi tới địa chỉ "+request.getValues().getEmail();
        return buildItemResponse(message);
    }

    @PostMapping("change-password")
    public ResponseEntity<?> changerPassword(@Valid @RequestBody ForgotPasswordRequest request){
        forgotPasswordService.changePassword(request);
        String message = "Đổi mật khẩu thành công";
        return buildItemResponse(message);
    }
}
