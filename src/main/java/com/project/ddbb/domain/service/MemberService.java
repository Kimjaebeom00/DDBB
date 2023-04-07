package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.MemberMapper;
import org.springframework.transaction.annotation.Transactional;

public class MemberService {
    MemberMapper memberMapper;
    public boolean accountPermit(final String id){
        try {
            return memberMapper.permit(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

