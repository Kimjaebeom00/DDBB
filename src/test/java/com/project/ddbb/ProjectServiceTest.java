package com.project.ddbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ddbb.domain.service.ProjectService;
import com.project.ddbb.domain.vo.ProjectVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    ProjectService projectService;

    @Test
    void save() {
        ProjectVO vo = new ProjectVO();
        vo.setMemberId(1L);
        vo.setTitle("뛰뛰빵빵");
        vo.setIntroduction("객체지향 프로그래밍2 팀프로젝트입니다.");

        projectService.save(vo);
    }

    @Test
    void findProjectsByUserId() {
        List<ProjectVO> projects = projectService.findProjectsByUserId(1L);
        try {
            String projectJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projects);
            System.out.println(projectJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() {
        ProjectVO vo = projectService.findByProjectId(1L);
        try {
            String projectJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(vo);
            System.out.println(projectJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        ProjectVO updateVo = new ProjectVO();
        updateVo.setProjectId(1L);
        updateVo.setTitle("뛰뛰빵빵");
        updateVo.setIntroduction("객체지향 프로그래밍2 팀프로젝트 수정합니다.");
        projectService.update(updateVo);

        ProjectVO resultVo = projectService.findByProjectId(1L);
        try {
            String projectJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(resultVo);
            System.out.println(projectJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        projectService.deleteById(2L);
    }
}
