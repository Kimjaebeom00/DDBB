package com.project.ddbb.controller;

import com.project.ddbb.domain.service.ProjectService;
import com.project.ddbb.domain.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
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
    @GetMapping("/add")
    public String addProject() {
        return "layout/project/add";
    }

    /**
     * 프로젝트 추가 처리
     * @param model
     * @return
     */
    @PostMapping("/add")
    public String addProjectProcess(Model model) {

        return "layout/project/info";
    }

    /**
     * 프로젝트 상세정보
     * @param projectId
     * @param model
     * @return
     */
    @PostMapping("/info")
    public String projectInfo(@RequestParam("projectId") Long projectId, Model model) {
        ProjectVO project = projectService.findById(projectId);

        model.addAttribute("project", project);
        return "layout/project/info";
    }
}
