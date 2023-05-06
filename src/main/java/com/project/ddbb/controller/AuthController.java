package com.project.ddbb.controller;

import com.project.ddbb.domain.service.BoardService;
import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.web.bind.annotation.ResponseBody;


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
            System.out.println("2");
            return "auth/sign_up";
        }
    }

    /**
     * ID 유효성 검증
     * @return
     */
    @PostMapping("/idValidation")
    @ResponseBody
    public boolean idValidation(MemberVO memberVO) throws Exception {
        boolean id;
        id = memberService.accountPermitId(memberVO.getId());
        return id;
    }
    /**
     * ID 찾기화면
     * @return
     */
    @GetMapping("/signFindId")
    public String signFindId() {

        return "auth/sign_findId";
    }
    /**
     * ID 찾기
     * @return
     */
    @PostMapping("/signFindId")
    @ResponseBody
    public String signFindIdProcess(MemberVO memberVO) throws Exception {
        if (memberService.findId(memberVO.getName(), memberVO.getEmail()) != null) {
            String id = memberService.findId(memberVO.getName(), memberVO.getEmail());
            System.out.println(memberService.findId(memberVO.getName(), memberVO.getEmail()));
            return id;
        }
        else {
            System.out.println("not find id");
        }
        return "auth/sign_findId";

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
    @ResponseBody
    public boolean signFindPasswordProcess(MemberVO memberVO) throws Exception {
        boolean id;
        // 아이디, 닉네임, 이메일 모두 일치하면 if문 실행 (true 반환)
         id = memberService.findPw(memberVO.getId(), memberVO.getNickname(), memberVO.getEmail());
            // 임시 비밀번호 생성
            if(id==true) {
                String TempPassword = memberService.CreateTempPassword();
                // 임시 비밀번호 메일로 보내기
                memberService.SendMail(memberVO.getEmail(), TempPassword);
                // 임시 비밀번호 암호화
                TempPassword = memberService.PassWordEncrypt(TempPassword);
                // 비밀번호 -> 임시 비밀번호 값으로 변경
                memberService.updatePassword(memberVO.getId(), TempPassword);
            }

        return id;
    }

    /**
     * 로그인 만료 페이지로 이동
     * @return
     */
    @GetMapping("/auth/error")
    public String authError(){
        return "redirect:/signIn";
    }

    /**
     * Email 유효성 검증
     * @return
     */

    @PostMapping("/emailValidation")
    @ResponseBody
    public boolean emailValidation(MemberVO memberVO) throws Exception {
        boolean email;
        email = memberService.accountPermitEmail(memberVO.getEmail());
        return email;
    }
}
