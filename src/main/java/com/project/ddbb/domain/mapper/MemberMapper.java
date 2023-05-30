package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    //회원가입
    void signUp(MemberVO vo) throws Exception;

    // DB에 저장된 정보 없으면 true, 있으면 false
    boolean permitId(String id) throws Exception;
    boolean permitPw(String pw) throws Exception;
    boolean permitEmail(String email) throws Exception;
    boolean permitName(String name) throws Exception;
    String findId(String name, String email) throws Exception;
    boolean findPw(String id, String email, String question, String answer) throws Exception;
    MemberVO selectById(String id) throws Exception;

    void updatePassword(String id, String password) throws Exception;

}
