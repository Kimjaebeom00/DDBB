package com.project.ddbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ddbb.controller.ProjectMemberController;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProjectMemberControllerTest {
    @Autowired
    ProjectMemberController projectMemberController;

    @Test
    @DisplayName("프로젝트 참여자 조회")
    public void projectMembers(){
        List<ProjectMemberVO> projectMemberList = projectMemberController.projectMembers(38L);

        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectMemberList);
            System.out.println(postJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("프로젝트 참여자 등록")
    public void addProjectMember(){
        ProjectMemberVO pmv = new ProjectMemberVO();
        pmv.setProjectId(38L);
        pmv.setMemberId(2L);

        projectMemberController.addProjectMember(pmv);
    }

    @Test
    @DisplayName("프로젝트 참여자 삭제")
    public void deleteProjectMember(){
        ProjectMemberVO pmv = new ProjectMemberVO();
        pmv.setProjectId(38L);
        pmv.setMemberId(2L);

        projectMemberController.deleteProjectMember(pmv);
    }

    
}
