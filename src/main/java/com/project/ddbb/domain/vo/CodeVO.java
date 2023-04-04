package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeVO {
    private Long codeId;             // 코드 고유번호
    private Long projectId;             // 프로젝트 고유번호
    private String title;               // 파일 제목
    private String beforeCode;          // 이전 코드
    private String currentCode;         // 현재 코드
}