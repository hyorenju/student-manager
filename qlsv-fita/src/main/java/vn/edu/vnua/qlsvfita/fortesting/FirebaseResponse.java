package vn.edu.vnua.qlsvfita.fortesting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FirebaseResponse {
    private String message;
    private String tempUrl;
    private String code;

    public FirebaseResponse(String message, String tempUrl) {
        this.message = message;
        this.tempUrl = tempUrl;
    }

    public FirebaseResponse(String message, Exception e, String code) {
        this.message = message;
        this.code = code;
    }
}
