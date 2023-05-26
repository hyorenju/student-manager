package vn.edu.vnua.qlsvfita.service.client;

import vn.edu.vnua.qlsvfita.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticateUser(String id, String password);

}
