package com.project.ddbb;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AuthControllerTest {

    @Autowired
    MemberMapper memberMapper;
    MemberService memberService;

    @Test
    public void signInProcess() throws Exception{
        System.out.println(memberMapper.permitid("Ktest"));
        }
    }
