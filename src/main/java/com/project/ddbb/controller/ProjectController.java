package com.project.ddbb.controller;

import com.project.ddbb.domain.service.ProjectService;
import com.project.ddbb.domain.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    /**
     * 메인화면
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String main(Model model) {
        Long userId = 1L; // 로그인한 사용자 아이디
        List<ProjectVO> projects = projectService.findProjectsByUserId(userId);

        model.addAttribute("projects", projects);
        return "layout/main/home";
    }

    /**
     * 프로젝트 추가 화면
     * @return
     */
    @GetMapping("/addProject")
    public String addProject() {
        return "layout/project/add";
    }

    /**
     * 프로젝트 추가 처리
     * @param model
     * @return
     */
    @PostMapping("/addProject")
    public String addProjectProcess(Model model) {

        return "layout/project/info";
    }
}
