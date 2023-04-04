package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberVO {

    private Long member_id;                    // PK
    private String name;               // 제목
    private String id;             // 내용
    private String password;              // 작성자
    private String phone;                // 조회 수
    private String email;           // 공지글 여부

}