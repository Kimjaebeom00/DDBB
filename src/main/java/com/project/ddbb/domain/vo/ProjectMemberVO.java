package com.project.ddbb.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberVO {
    private Long projectId;         // 프로젝트 고유번호
    private Long memberId;          // 사용자 고유번호
}
