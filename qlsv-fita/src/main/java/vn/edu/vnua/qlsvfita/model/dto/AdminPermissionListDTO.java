package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminPermissionListDTO {
    private String roleId;
    private List<Boolean> permissions;
}
