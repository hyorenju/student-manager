package vn.edu.vnua.qlsvfita.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PermissionCheckResponse {

    private Map<String, Boolean> checkingPermissions;
}
