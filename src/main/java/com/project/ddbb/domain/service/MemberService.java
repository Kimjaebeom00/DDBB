package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.vo.MemberVO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    /**
     * id 검증
     * @param id
     * @return
     */
    public boolean accountPermitId(final String id) throws Exception{
        return memberMapper.permitId(id);
    }

    /**
     * password 검증
     * @param pw
     * @return
     */
    public boolean accountPermitPw(final String pw) throws Exception{
        return memberMapper.permitPw(pw);
    }

    public boolean accountPermitEmail(final String email) throws Exception{
        return memberMapper.permitemail(email);
    }
    public boolean accountPermitName(final String name) throws Exception{
        return memberMapper.permitname(name);
    }

    public String findId(final String name, String email) throws Exception{
        return memberMapper.findId(name, email);
    }

    public void SignUp(final MemberVO memberVO) throws Exception {
        memberMapper.signUp(memberVO);
    }

    /**
     * 비밀번호 암호화
     * @param pwd
     * @return
     * @throws NoSuchAlgorithmException
     */

    // SHA-256 알고리즘 적용
    public String PassWordEncrypt(final String pwd) {
        String result = "";
        Supplier<String> getSalt = () -> {
            SecureRandom sr = new SecureRandom();
            byte[] salt = new byte[20];
            sr.nextBytes(salt);
            StringBuilder sb = new StringBuilder();
            for (byte b : salt) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        };

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            System.out.println("PWD + SALT 적용 전 : " + pwd + getSalt.get());
            md.update((pwd + getSalt.get()).getBytes());
            byte[] pwdSalt = md.digest();

            StringBuffer sb = new StringBuffer();
            for(byte b : pwdSalt) {
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();
            System.out.println("PWD + SALT 적용 후 : " + result);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    //public String PassWordEncrypt(final String pw) throws NoSuchAlgorithmException {
        /*MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] dataBytes = pw.getBytes(StandardCharsets.UTF_8);
        digest.update(dataBytes);
        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();*/
    //}

    public MemberVO selectById(final String id) throws Exception {
        return memberMapper.selectById(id);
    }

    public void updatePassword(String id, String password) throws Exception{
        memberMapper.updatePassword(id, password);
    }


    public String CreateTempPassword() {
        final String NUMBERS = "0123456789";
        final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomType = random.nextInt(3); // 0:숫자, 1:소문자, 2:대문자
            switch (randomType) {
                case 0:
                    sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
                    break;
                case 1:
                    sb.append(LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length())));
                    break;
                case 2:
                    sb.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
                    break;
            }
        }
        return sb.toString();
    }


    public void SendMail(String email, String TempPassword) throws MessagingException {
        String host = "smtp.naver.com";
        int port = 587;
        String username = "inhaddbb";
        String password = "inhaddbb1234!";
        String from = "inhaddbb@naver.com";
        String to = email;
        String subject = "프로젝트 관리 프로그램 임시 비밀번호 발급";
        String content = "";
        content += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">임시 비밀번호</h1>";
        content += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 비밀번호란에 입력해주세요.</p>";
        content += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        content += TempPassword;
        content += "</td></tr></tbody></table></div>";

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
        message.setText(content, "utf-8", "html");

        Transport.send(message);
    }
}

