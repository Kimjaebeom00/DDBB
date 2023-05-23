package com.project.ddbb.controller;

import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.service.ProjectMemberService;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projectMember")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;
    private final MemberService memberService;


    /**
     * 프로젝트 참여자 등록
     * @param addId
     */
    @PostMapping("/add")
    public String addProjectMember(RedirectAttributes redirect, @RequestParam String addId, @RequestParam Long projectId) throws Exception {
        if (memberService.accountPermitId(addId)) {
            ProjectMemberVO pmv = new ProjectMemberVO();
            pmv.setMemberId(memberService.selectById(addId).getMemberId());
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
     * @param deleteId
     */
    @PostMapping("delete")
    public String deleteProjectMember(RedirectAttributes redirect, @RequestParam String deleteId, @RequestParam Long projectId) throws Exception {
        Long memId = memberService.selectById(deleteId).getMemberId();
        ProjectMemberVO vo = projectMemberService.findByProjectMember(memId, projectId);
        if(vo != null)
            projectMemberService.delete(vo);

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info";
    }
}
