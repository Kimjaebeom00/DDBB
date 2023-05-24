package com.project.ddbb.domain.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {

    private Long memberId;
    private String id;
    private String password;
    private String name;
    private String email;
    private String question;
    private String answer;
}
