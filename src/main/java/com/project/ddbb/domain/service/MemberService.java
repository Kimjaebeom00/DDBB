package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.MemberMapper;
import com.project.ddbb.domain.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

