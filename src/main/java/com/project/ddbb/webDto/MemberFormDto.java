package com.project.ddbb.webDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
// 회원가입 화면으로부터 넘어오는 가입정보를 담는 클래스
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phone;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;


    @Builder
    public MemberFormDto(String name, String id, String password, String phone, String email) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }
}