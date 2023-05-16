package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardVO {
    private Long commentId;                      // PK, 코멘트 고유번호
    private Long projectId;                      // FK, 프로젝트 고유번호
    private Long memberId;                       // FK, 사용자 고유번호
    private String content;                      // 코멘트 내용
    private LocalDateTime createdDate;           // 최초 작성 일자
    private LocalDateTime updatedDate;           // 삭제 여부 판단
    private int status;                          // 최종 수정 일시
}