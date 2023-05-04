package com.project.ddbb;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@SpringBootTest
public class AuthControllerTest {

    @Autowired
    MemberMapper memberMapper;


    @Test
    public void signInProcess() throws Exception {
        System.out.println(memberMapper.permitid("Ktest"));
    }

}
