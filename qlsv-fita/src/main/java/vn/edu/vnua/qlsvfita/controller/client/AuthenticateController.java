package vn.edu.vnua.qlsvfita.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.request.client.LoginRequest;
import vn.edu.vnua.qlsvfita.response.LoginResponse;
import vn.edu.vnua.qlsvfita.service.client.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class AuthenticateController extends BaseController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authenticationService.authenticateUser(request.getId(), request.getPassword());
        return buildItemResponse(response);
    }
}
