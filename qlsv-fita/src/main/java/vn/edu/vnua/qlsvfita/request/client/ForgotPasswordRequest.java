package vn.edu.vnua.qlsvfita.request.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.vnua.qlsvfita.request.ChangePasswordRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "Truyền mã user id vào nè")
    private String id;

    private ChangePasswordValue values;

    @Data
    @AllArgsConstructor
    public static class ChangePasswordValue{
        @NotBlank(message = "Vui lòng nhập mật khẩu mới")
        private String newPassword;

        @NotBlank(message = "Vui lòng xác nhận mật khẩu")
        private String confirmPassword;
    }
}
