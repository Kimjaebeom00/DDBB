package com.project.ddbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.vo.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MemberMapperTest {
    @Autowired
    MemberMapper memberMapper;
    MemberVO memberVO;


    @Test
    void memberJoin() throws Exception{
        memberVO.setId("Ktest");
        memberVO.setPassword("Ktest");
        memberVO.setName("Ktest");
        memberVO.setEmail("Ktest");
        memberMapper.signup(memberVO);
        System.out.println(memberVO.toString());

    }

    @Test
    void accountPermit() throws Exception{
        boolean post = memberMapper.permit("K");

        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
