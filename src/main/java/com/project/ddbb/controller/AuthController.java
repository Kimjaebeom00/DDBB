package com.project.ddbb.controller;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;

@Controller
@RequiredArgsConstructor
public class AuthController {

    MemberMapper memberMapper;

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
    @PostMapping("/signIn")
    public String signInProcess(MemberVO memberVO) throws Exception{
        String id = "Ktest";
        String password = "Ktest";
//        System.out.println(memberVO.getId());
//        System.out.println(memberMapper.permitid(id));
//        System.out.println(memberVO.getPassword());

        MemberService memberService = new MemberService();
//         userid와 password 검증 로직
        if (id != null && password != null && !id.isEmpty() && !password.isEmpty()) {
//             로그인 성공
            if (memberMapper.permitid(id)){
                System.out.println("Success");
                return "redirect:/project/home";
            } else {
                System.out.println("Fail");
                return "redirect:/signIn";
            }
        } else {
            // 로그인 실패
            System.out.println("Fail2");
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
