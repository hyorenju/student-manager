package vn.edu.vnua.qlsvfita.request.admin.role;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateRoleRequest {
    @NotNull(message = "id không được để trống")
    private String id;
//    private String name;
    private List<String> permissionIds;
}
