package com.project.ddbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ddbb.controller.ProjectMemberController;
import com.project.ddbb.domain.service.ProjectMemberService;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class ProjectMemberControllerTest {
    @Autowired
    ProjectMemberController projectMemberController;
    @Autowired
    ProjectMemberService projectMemberService;

    @Test
    @DisplayName("프로젝트 참여자 조회")
    public void projectMembers(){
        List<Map<String, Object>> projectMemberList = projectMemberService.findByProjectId(1L);

        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectMemberList);
            System.out.println(postJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("프로젝트 참여자 등록")
    public void addProjectMember(RedirectAttributes redirect) throws Exception {
        ProjectMemberVO pmv = new ProjectMemberVO();

        projectMemberController.addProjectMember(redirect, "33333", 1L);
    }

    @Test
    @DisplayName("프로젝트 참여자 삭제")
    public void deleteProjectMember(){
        ProjectMemberVO pmv = new ProjectMemberVO();
        pmv.setProjectId(38L);
        pmv.setMemberId(2L);

//        projectMemberController.deleteProjectMember(pmv);
    }

    @Test
    public void findByMember() {
        ProjectMemberVO projectMember = projectMemberService.findByProjectMember(1L, 1L);
        System.out.println("---------------------------------------------");
        System.out.println("---------------------------------------------");
        System.out.println(projectMember);
        System.out.println("---------------------------------------------");
        System.out.println("---------------------------------------------");


    }


}
