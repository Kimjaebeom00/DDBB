package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectVO {
    private Long projectId;         // 프로젝트 고유번호
    private Long memberId;          // 사용자 고유번호
    private String title;           // 제목
    private String introduction;    // 소개
    private boolean deleteYn;       // 삭제여부
    private int leaderYn;          // 리더자 여부
}
