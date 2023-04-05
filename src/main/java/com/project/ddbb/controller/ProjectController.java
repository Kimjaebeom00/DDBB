package com.project.ddbb.controller;

import com.project.ddbb.domain.service.ProjectMemberService;
import com.project.ddbb.domain.service.ProjectService;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import com.project.ddbb.domain.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;

    /**
     * 메인화면
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String main(Model model) {
        Long userId = 1L; // 로그인한 사용자 아이디, Spring Security 적용 시 삭제
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
     * @param vo
     * @param redirect
     * @return
     */
    @PostMapping("/add")
    public String addProjectProcess(ProjectVO vo, RedirectAttributes redirect) {
        vo.setMemberId(1L); // Spring Security 적용 시 삭제
        Long projectId = projectService.save(vo);

        ProjectMemberVO pmv = new ProjectMemberVO();
        pmv.setProjectId(vo.getProjectId());
        pmv.setMemberId(vo.getMemberId());
        pmv.setLeaderYn(true);
        projectMemberService.save(pmv);

        redirect.addFlashAttribute("projectId", projectId);
        return "redirect:/project/info";
    }

    /**
     * 프로젝트 생성 후 프로젝트 상세정보
     * @param model
     * @return
     */
    @GetMapping("/info")
    public String projectInfoAfterAddProject(Model model) {
        ProjectVO project = projectService.findByProjectId((Long) model.getAttribute("projectId"));

        model.addAttribute("project", project);
        return "layout/project/info";
    }

    /**
     * 프로젝트 상세정보
     * @param model
     * @return
     */
    @PostMapping("/info")
    public String projectInfo(@RequestParam("projectId") Long projectId, Model model) {
        ProjectVO project = projectService.findByProjectId(projectId);

        model.addAttribute("project", project);
        return "layout/project/info";
    }
}
