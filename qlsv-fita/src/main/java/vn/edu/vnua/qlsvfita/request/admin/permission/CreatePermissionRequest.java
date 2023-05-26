package vn.edu.vnua.qlsvfita.request.admin.permission;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePermissionRequest {
    @NotBlank(message = "Mã vai trò không được để trống")
    private String id;

    @NotBlank(message = "Tên vai trò mô tả công việc được cho phép, không được để trống")
    private String name;
}
