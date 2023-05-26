package vn.edu.vnua.qlsvfita.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String jwt;
    private String id;
    private String username;
    private String email;
    private String roleId;
}
