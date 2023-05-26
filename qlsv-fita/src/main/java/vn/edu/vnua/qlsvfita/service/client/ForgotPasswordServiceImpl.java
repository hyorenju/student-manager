package vn.edu.vnua.qlsvfita.service.client;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Admin;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.repository.AdminRepository;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.request.client.ForgotPasswordRequest;
import vn.edu.vnua.qlsvfita.request.client.SendMailRequest;

import java.util.Objects;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder encoder;

    @Override
    public void sendMessage(SendMailRequest request){
//        if(!adminRepository.existsById(request.getId())
//        && !studentRepository.existsById(request.getId())){
//            throw new EntityNotFoundException("Tài khoản hoặc email không trùng khớp");
//        }

        if(adminRepository.existsById(request.getValues().getId())){
            Admin admin = adminRepository.getById(request.getValues().getId());
            if(admin.equals(request.getValues().getEmail())){
                sendMimeEmail(request.getValues().getEmail(), request.getLink());
//                admin.setPassword("123");
//                adminRepository.saveAndFlush(admin);
            } else {
                throw new RuntimeException("Tài khoản hoặc email không trùng khớp");
            }
        } else if(studentRepository.existsById(request.getValues().getId())){
            Student student = studentRepository.getStudentById(request.getValues().getId());
            if(student.getEmail().equals(request.getValues().getEmail())){
                sendMimeEmail(request.getValues().getEmail(), request.getLink());
//                student.setPassword("123");
//                studentRepository.saveAndFlush(student);
            } else {
                throw new RuntimeException("Tài khoản hoặc email không trùng khớp");
            }
        } else {
            throw new RuntimeException("Tài khoản hoặc email không trùng khớp");
        }
    }

    @Override
    public void changePassword(ForgotPasswordRequest request) {
        if(studentRepository.existsById(request.getId())) {
            Student student = studentRepository.getStudentById(request.getId());
            if (encoder.matches(request.getValues().getNewPassword(), student.getPassword())) {
                throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
            }
            if (!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())) {
                throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
            }
            student.setPassword(encoder.encode(request.getValues().getNewPassword()));
            studentRepository.saveAndFlush(student);
        } else if(adminRepository.existsById(request.getId())){
            Admin admin = adminRepository.getById(request.getId());
            if(encoder.matches(request.getValues().getNewPassword(), admin.getPassword())){
                throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
            }
            if(!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())){
                throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
            }
            admin.setPassword(encoder.encode(request.getValues().getNewPassword()));
            adminRepository.saveAndFlush(admin);
        } else {
            throw new RuntimeException("Người dùng không tồn tại");
        }
    }

    private static void sendMimeEmail(String email, String link) {

        final String username = "hyorenju@gmail.com";
        final String password = "aumjtgpnkmxhafwr";

        Properties props = new Properties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String subject = "Yêu cầu đồi mật khẩu";
        String text = "Bạn đã gửi một yêu cầu đổi mật khẩu. Hãy truy cập đường dẫn sau để đổi mật khẩu:" +
                "\n\n" + link +
                "\n\nVui lòng không chia sẻ link này cho bất cứ ai nếu bạn không muốn người khác đổi mật khẩu của mình." +
                "\n\nNếu bạn không yêu cầu đổi mật khẩu, vui lòng bỏ qua bước này.";

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(text);

//            Transport transport = session.getTransport("smtp");
//            transport.connect("smtp.gmail.com", 465, username, password);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
