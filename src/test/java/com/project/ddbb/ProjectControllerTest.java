package com.project.ddbb;

import com.project.ddbb.controller.ProjectController;
import com.project.ddbb.domain.service.ProjectService;
import com.project.ddbb.domain.vo.ProjectVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Map;

@SpringBootTest
public class ProjectControllerTest {
    @Autowired
    ProjectController projectController;

    @Test
    void addProjectProcess(){
        ProjectVO vo = new ProjectVO();
        vo.setTitle("테스트 코드 프로젝트");
        vo.setIntroduction("테스트 코드로 등록한 프로젝트입니다.");

        projectController.addProjectProcess(vo, new RedirectAttributes() {
            @Override
            public RedirectAttributes addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public RedirectAttributes addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public RedirectAttributes addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public RedirectAttributes mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public RedirectAttributes addFlashAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public RedirectAttributes addFlashAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Map<String, ?> getFlashAttributes() {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        });
    }
}
