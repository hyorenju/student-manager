package vn.edu.vnua.qlsvfita.request.admin.admin;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateAdminRequest {
    @NotBlank(message = "Mã quản trị viên không được để trống")
    @Size(max = 10, message = "Mã quản trị viên chỉ được tối đa 10 ký tự")
    @Pattern(regexp = "^[a-zA-Z]+", message = "Mã quản trị viên phải bắt đầu bằng ký tự chữ")
    private String id;

    @NotBlank(message = "Tên quản trị viên không được để trống")
    @Size(max = 200, message = "Tên quản trị viên chỉ được tối đa 200 ký tự")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 32, message = "Mật khẩu chỉ được tối đa 32 ký tự")
    private String password;

    @NotBlank(message = "Vai trò không được để trống")
    private String roleId;
}
