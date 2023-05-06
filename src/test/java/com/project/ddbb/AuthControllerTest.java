package com.project.ddbb;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.vo.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.function.Supplier;


@SpringBootTest
public class AuthControllerTest {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    MemberService memberService;


    @Test
    public void signInProcess() throws Exception {
        System.out.println(memberMapper.permitId("Ktest"));
    }

    @Test
    public void signFindPasswordProcess() throws Exception {
        System.out.println(1);
        String TempPassword = memberService.CreateTempPassword();
        System.out.println(TempPassword);
        memberService.updatePassword("rnjsdndud00", TempPassword);
        System.out.println(2);
        memberService.SendMail("rnjsdndud00@naver.com", TempPassword);
        System.out.println(3);
    }

    @Test
    public void PassWordEncrypt() {
        String pwd = "12432423";
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
    }

}
