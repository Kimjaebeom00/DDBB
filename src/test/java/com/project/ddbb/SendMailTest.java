package com.project.ddbb;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@SpringBootTest
public class SendMailTest
{
    @Test
    void sendNaverMail() throws Exception
    {
        String host = "smtp.naver.com";
        int port = 587;
        String username = "inhaddbb";
        String password = "inhaddbb1234!";
        String from = "inhaddbb@naver.com";
        String to = "rnjsdndud00@naver.com";
        String subject = "네이버 메일 전송 테스트";
        String content = "안녕하세요. 네이버 메일 API 테스트";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}
