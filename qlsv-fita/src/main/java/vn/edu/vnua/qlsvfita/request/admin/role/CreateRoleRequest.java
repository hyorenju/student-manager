package vn.edu.vnua.qlsvfita.request.admin.role;

import lombok.Data;

import java.util.List;

@Data
public class CreateRoleRequest {
    private String id;
    private String name;
    private List<String> permissionIds;
}
