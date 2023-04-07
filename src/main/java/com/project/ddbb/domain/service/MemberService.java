package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.MemberMapper;

public class MemberService {
    MemberMapper memberMapper;
    public boolean accountPermitId(final String id){
        try {
            return memberMapper.permitid(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean accountPermitPw(final String pw){
        try {
            return memberMapper.permitpw(pw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

