package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeCompareVO {
    private Long projectId;             // PK
    private String title;               // 파일 제목
    private String beforeCode;          // 이전 코드
    private String currentCode;         // 현재 코드
}