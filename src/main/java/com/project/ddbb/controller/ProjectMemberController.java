package com.project.ddbb.controller;

import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.service.ProjectMemberService;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projectMember")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;
    private final MemberService memberService;


    /**
     * 프로젝트 참여자 등록
     * @param id
     */
    @PostMapping("/add")
    public String addProjectMember(RedirectAttributes redirect, @RequestParam String id, @RequestParam Long projectId) throws Exception {
        if (memberService.accountPermitId(id)) {
            ProjectMemberVO pmv = new ProjectMemberVO();
            pmv.setMemberId(memberService.selectById(id).getMemberId());
            pmv.setProjectId(projectId);
            pmv.setLeaderYn(false);
            projectMemberService.save(pmv);
        } else {
            redirect.addAttribute("projectId", projectId);
            return "redirect:/project/info";
        }
        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info";
    }

    /**
     * 프로젝트 참여자 삭제
     * @param vo
     */
    @PostMapping("delete")
    @ResponseBody
    public void deleteProjectMember(@RequestParam("ProjectMemberVO") ProjectMemberVO vo) {
        projectMemberService.delete(vo);
    }
}
