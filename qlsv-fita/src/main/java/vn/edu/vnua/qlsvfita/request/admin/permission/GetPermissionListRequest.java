package vn.edu.vnua.qlsvfita.request.admin.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetPermissionListRequest {
    @NotNull(message = "Start không được để trống")
    private Integer page;
    @NotNull(message = "Limit không được để trống")
    private Integer size;
}
