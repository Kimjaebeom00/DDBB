package com.project.ddbb.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    /**
     * 로그인 화면
     * @return
     */
    @GetMapping("/signIn")
    public String signIn() {

        return "auth/sign_in";
    }

    /**
     * 로그인 처리
     * @return
     */
//    @PostMapping("/signIn")
//    public String signInProcess() {
//
//        return "redirect:/project/home";
//    }
    /**
     * 로그인 유효성 처리
     * @return
     */

    @PostMapping("/signIn")
    public String signInProcess(HttpServletRequest request) {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        // userid와 password 검증 로직
        if (userid != null && password != null && !userid.isEmpty() && !password.isEmpty()) {
            // 로그인 성공
            return "redirect:/project/home";
        } else {
            // 로그인 실패
            return "redirect:/signIn";
        }
    }


    /**
     * 회원가입 화면
     * @return
     */
    @GetMapping("/signUp")
    public String signUp() {

        return "auth/sign_up";
    }

    /**
     * 회원가입 처리
     * @return
     */
    @PostMapping("/signUp")
    public String signUpProcess() {

        return "auth/sign_up_complete";
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
