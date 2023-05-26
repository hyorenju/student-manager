package vn.edu.vnua.qlsvfita.service.client;

import vn.edu.vnua.qlsvfita.request.client.ForgotPasswordRequest;
import vn.edu.vnua.qlsvfita.request.client.SendMailRequest;


public interface ForgotPasswordService {
    void sendMessage(SendMailRequest request);
    void changePassword(ForgotPasswordRequest request);
}
