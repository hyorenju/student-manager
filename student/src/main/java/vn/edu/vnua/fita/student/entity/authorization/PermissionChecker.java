package vn.edu.vnua.fita.student.entity.authorization;

import lombok.Data;

import java.util.Map;

@Data
public class PermissionChecker {

    private Map<String, Boolean> checkingPermissions;
}
