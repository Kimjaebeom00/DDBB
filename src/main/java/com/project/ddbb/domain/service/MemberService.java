package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        return memberMapper.permitid(id);
    }

    /**
     * password 검증
     * @param pw
     * @return
     */
    public boolean accountPermitPw(final String pw) throws Exception{
        return memberMapper.permitpw(pw);
    }

    public void SignUp(final MemberVO memberVO) throws Exception {
        memberMapper.signup(memberVO);
    }

    /**
     * 비밀번호 암호화
     * @param pw
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String PassWordEncrypt(final String pw) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] dataBytes = pw.getBytes(StandardCharsets.UTF_8);
        digest.update(dataBytes);
        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println("진짜값" + hexString.toString());
        return hexString.toString();
    }


}

