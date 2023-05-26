package vn.edu.vnua.qlsvfita.request.client;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendMailRequest {
    @NotBlank(message = "Front-end dev vui lòng truyền về đường dẫn đổi mật khẩu")
    private String link;

    private SendMailValue values;

    @Data
    public static class SendMailValue{
        @NotBlank(message = "Nhập tài khoản của bạn")
        private String id;

        @NotBlank(message = "Nhập email bạn đã đăng ký cho tài khoản trên")
        private String email;
    }
}
