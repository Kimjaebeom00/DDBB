package com.project.ddbb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    /**
     * 로그인
     * @return
     */
    @PostMapping("/signIn")
    public String signIn() {

        return "auth/signIn";
    }

    /**
     * 회원가입
     * @return
     */
    @PostMapping("/signUp")
    public String signUp() {

        return "auth/signUp";
    }

    /**
     * ID 유효성 검증
     * @return
     */
    @PostMapping("/idValidation")
    public boolean idValidation() {

        return true;
    }
}
