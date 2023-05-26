package vn.edu.vnua.qlsvfita.validator.exception;

import lombok.Data;

@Data
public class JwtTokenInvalid extends RuntimeException {
    public JwtTokenInvalid(String message) {
        super(message);
    }
}
