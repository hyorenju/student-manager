package vn.edu.vnua.fita.student.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.edu.vnua.fita.student.common.ErrorCodeDefs;
import vn.edu.vnua.fita.student.response.BaseResponse;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setFailed(ErrorCodeDefs.TOKEN_INVALID, ErrorCodeDefs.getErrMsg(ErrorCodeDefs.TOKEN_INVALID));
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, baseResponse);
        responseStream.flush();
    }


}
