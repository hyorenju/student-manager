package vn.edu.vnua.qlsvfita.request.admin.admin;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateAdminRequest {
    @NotBlank(message = "Mã quản trị viên không được để trống")
    @Size(max = 10, message = "Mã quản trị viên chỉ được tối đa 10 ký tự")
    private String id;

    @NotBlank(message = "Tên quản trị viên không được để trống")
    @Size(max = 200, message = "Tên quản trị viên chỉ được tối đa 200 ký tự")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;

    @Size(max = 32, message = "Mật khẩu chỉ được tối đa 32 ký tự")
    private String password;

    @NotBlank(message = "Quyền không được để trống")
    private String roleId;
}
