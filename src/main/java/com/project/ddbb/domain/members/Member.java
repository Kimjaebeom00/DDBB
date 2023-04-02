package com.project.ddbb.domain.members;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.project.ddbb.webDto.MemberFormDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "member")
@Entity
// 회원정보 저장 클래스
public class Member {

    @Id
    @GeneratedValue
    private Long idx;

    private String name;

    @Column(unique = true)
    private String id;

    private String password;

    private String phone;

    private String email;

//    @Enumerated(EnumType.STRING)  // enum 이름 값(role)을 DB에 저장
//    private MemberRole role;

    @Builder
    public Member(String name, String id, String password, String phone, String email) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .name(memberFormDto.getName())
                .id(memberFormDto.getId())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))  //암호화처리
                .phone(memberFormDto.getPhone())
                .email(memberFormDto.getEmail())
                .build();
        return member;
    }
}