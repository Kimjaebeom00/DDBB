package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    //회원가입
    void signup(MemberVO vo) throws Exception;

    // DB에 저장된 정보 있으면 home 화면으로 이동
    boolean permitid(String id) throws Exception;
    boolean permitpw(String pw) throws Exception;
    MemberVO selectById(String id) throws Exception;

}
