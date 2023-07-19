//package vn.edu.vnua.qlsvfita.security.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfig {
//
//    @Value("${spring.mail.username}")
//    private String emailUsername;
//
//    @Value("${spring.mail.password}")
//    private String emailPassword;
//
//    @Value("${spring.mail.host}")
//    private String emailHost;
//
//    @Value("${spring.mail.port}")
//    private int emailPort;
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(emailHost);
//        mailSender.setPort(emailPort);
//        mailSender.setUsername(emailUsername);
//        mailSender.setPassword(emailPassword);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//
//        return mailSender;
//    }
//}
//
