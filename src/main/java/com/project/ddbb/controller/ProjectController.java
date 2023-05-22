package com.project.ddbb.controller;

import com.project.ddbb.domain.mapper.ProjectMapper;
import com.project.ddbb.domain.service.ProjectMemberService;
import com.project.ddbb.domain.service.ProjectService;
import com.project.ddbb.domain.vo.MemberVO;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import com.project.ddbb.domain.vo.ProjectVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
     *
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String projectMain(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

        List<ProjectVO> projects = projectService.findProjectsByUserId(memberInfo.getMemberId());

        model.addAttribute("projects", projects);
        model.addAttribute("isLnb", false);

        return "layout/main/home";
    }

    /**
     * 프로젝트 추가 화면
     *
     * @return
     */
    @GetMapping("/add")
    public String addProject(Model model, HttpServletRequest request) {
        model.addAttribute("isLnb", false);

        return "layout/project/add";
    }


    /**
     * 프로젝트 추가 처리
     *
     * @param vo
     * @param redirect
     * @return
     */
    @PostMapping("/add")
    public String addProjectProcess(ProjectVO vo, RedirectAttributes redirect, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

        Long memberId = memberInfo.getMemberId();

        vo.setMemberId(memberId);
        Long projectId = projectService.save(vo);

        ProjectMemberVO pmv = new ProjectMemberVO();
        pmv.setProjectId(projectId);
        pmv.setMemberId(memberId);
        pmv.setLeaderYn(1);
        projectMemberService.save(pmv);

        redirect.addAttribute("projectId", projectId);

        return "redirect:/project/info";
    }


    /**
     * 프로젝트 수정 처리
     */
    @PostMapping("/modify")

    public String modifyProjectProcess(@RequestParam("projectId") Long projectId, ProjectVO vo, RedirectAttributes redirect, HttpServletRequest request) {

        HttpSession session = request.getSession();
        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
        Long memberId = memberInfo.getMemberId();

        vo.setMemberId(memberId);
        projectService.update(vo);

        redirect.addAttribute("projectId", projectId);

        return "redirect:/project/home";
    }


    /**
     * 프로젝트 상세정보
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/info")
    public String projectInfo(@RequestParam(required = false, name = "projectId") Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

        Long projectId = (id == null) ? (Long) model.getAttribute("id") : id;

        ProjectVO project = projectService.findByProjectId(projectId);
        List<ProjectVO> projects = projectService.findProjectsByUserId(memberInfo.getMemberId());

        model.addAttribute("project", project);
        model.addAttribute("projects", projects);

        return "layout/project/info";
    }


    @GetMapping("/modify")
    public String modify(@RequestParam("projectId") Long projectId, Model model, @RequestParam("title") String title, @RequestParam("introduction") String introduction, @RequestParam("leaderYn") int leaderYn) {

        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        model.addAttribute("introduction", introduction);
        model.addAttribute("isLnb", false);
        model.addAttribute("leaderYn", leaderYn);

        return "layout/project/modify";
    }


    /**
     * 프로젝트 삭제
     *
     * @param
     * @param
     * @param
     * @return
     */


    @PostMapping("/delete")
    public String deleteProjectProcess(ProjectVO vo, RedirectAttributes redirect, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

        Long memberId = memberInfo.getMemberId();
        Long projectId = vo.getProjectId();
        vo.setMemberId(memberId);
        vo.setProjectId(projectId);

        // 프로젝트 삭제
        projectService.deleteById(projectId);

        // 삭제된 프로젝트의 ID를 redirect 플래시 속성으로 추가
        redirect.addFlashAttribute("projectId", projectId);

        return "redirect:/project/home";
    }


}
//    @PostMapping("/delete")
//    public String deleteProjectProcess(@RequestParam("projectId") Long projectId, RedirectAttributes redirect, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
//        Long memberId = memberInfo.getMemberId();
//        ProjectMemberVO pmv = new ProjectMemberVO();
//
//        projectService.deleteById(projectId);
//
//        redirect.addFlashAttribute("projectId", projectId);
//
//        return "redirect:/project/home";
//    }







//    @PostMapping("/add")
//    public String addProjectProcess(ProjectVO vo, RedirectAttributes redirect, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
//
//        Long memberId = memberInfo.getMemberId();
//
//        vo.setMemberId(memberId);
//        Long projectId = projectService.save(vo);
//
//        ProjectMemberVO pmv = new ProjectMemberVO();
//        pmv.setProjectId(projectId);
//        pmv.setMemberId(memberId);
//        pmv.setLeaderYn(1);
//        projectMemberService.save(pmv);
//
//        redirect.addAttribute("projectId", projectId);
//
//        return "redirect:/project/info";
//    }
