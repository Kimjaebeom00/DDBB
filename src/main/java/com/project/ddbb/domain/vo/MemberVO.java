package com.project.ddbb.domain.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
    private String id;
    private String password;
    private String name;
    private String email;

//    public MemberVO(String id, String password, String name, String email) {
//        this.id = id;
//        this.password = password;
//        this.name = name;
//        this.email = email;
//    }
//
//    @Override
//    public String toString() {
//        return "MemberVO{" +
//                "id='" + id + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                '}';
//    }
}
