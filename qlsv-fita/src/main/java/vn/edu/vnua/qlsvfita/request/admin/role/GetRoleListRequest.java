package vn.edu.vnua.qlsvfita.request.admin.role;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetRoleListRequest {
    @NotNull(message = "Start không được để trống")
    private Integer page;
    @NotNull(message = "Limit không được để trống")
    private Integer size;
}
