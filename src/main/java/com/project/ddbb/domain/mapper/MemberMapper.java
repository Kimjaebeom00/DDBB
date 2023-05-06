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
//    boolean permitemail(String email) throws Exception;
//    boolean permitname(String name) throws Exception;
    String findId(String name, String email) throws Exception;
    MemberVO selectById(String id) throws Exception;

    void updatePassword(String id, String password) throws Exception;

}
