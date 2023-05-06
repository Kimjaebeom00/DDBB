package com.project.ddbb.controller;

import com.project.ddbb.domain.service.BoardService;
import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.vo.MemberVO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

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
    public String signInProcess(MemberVO memberVO, HttpServletRequest request) throws Exception{
//         id와 password 검증 로직
        if (memberVO.getId() != null && memberVO.getPassword() != null && !memberVO.getId().isEmpty() && !memberVO.getPassword().isEmpty()) {
//             로그인 성공
            if (memberService.accountPermitId(memberVO.getId()) && memberService.accountPermitPw(memberService.PassWordEncrypt(memberVO.getPassword()))){
                MemberVO memberInfo = memberService.selectById(memberVO.getId());

                HttpSession session = request.getSession();
                session.setAttribute("memberInfo", memberInfo);

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
    public String signUpProcess(MemberVO memberVO) throws Exception {
        if (memberVO.getEmail() != null && !memberVO.getEmail().isEmpty()){
            memberVO.setPassword(memberService.PassWordEncrypt(memberVO.getPassword()));
            memberService.SignUp(memberVO);
            return "auth/sign_up_complete";
        } else {
            return "auth/sign_up";
        }
    }

    /**
     * ID 유효성 검증
     * @return
     */
    @PostMapping("/idValidation")
    public boolean idValidation(MemberVO memberVO) throws Exception {

        return memberService.accountPermitId(memberVO.getId());
    }
    /**
     * ID 찾기
     * @return
     */
    @GetMapping("/signFindId")
    public String signFindId() {

        return "auth/sign_findId";
    }

    @PostMapping("/signFindId")
    public void signFindIdProcess(MemberVO memberVO) throws Exception {
        if (memberService.findId(memberVO.getName(), memberVO.getEmail()) != null) {
            System.out.println(memberService.findId(memberVO.getName(), memberVO.getEmail()));
        }
        else {
            System.out.println("not find id");
        }
    }
    /**
     * Password 찾기
     * @return
     */
    @GetMapping("/signFindPassword")
    public String signFindPassword() {

        return "auth/sign_findPassword";
    }
    @PostMapping("/signFindPassword")
    public String signFindPasswordProcess(MemberVO memberVO) throws Exception {
        // 임시 비밀번호 생성
        String TempPassword = memberService.CreateTempPassword();
        // 임시 비밀번호 메일로 보내기
        memberService.SendMail(memberVO.getEmail(), TempPassword);
        // 임시 비밀번호 암호화
        TempPassword = memberService.PassWordEncrypt(TempPassword);
        // 비밀번호 -> 임시 비밀번호 값으로 변경
        memberService.updatePassword(memberVO.getId(), TempPassword);

        return "auth/sign_findPassword";
    }
}
