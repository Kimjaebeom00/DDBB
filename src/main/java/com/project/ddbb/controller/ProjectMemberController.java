package com.project.ddbb.controller;

import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.service.ProjectMemberService;
import com.project.ddbb.domain.vo.MemberVO;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import com.project.ddbb.domain.vo.ProjectVO;
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
     * @param vo
     */
    @PostMapping("/add")
    @ResponseBody
    public void addProjectMember(@RequestParam("ProjectMemberVO") ProjectMemberVO vo, @RequestParam String id) throws Exception {
        vo.setMemberId(memberService.selectById(id).getMemberId());
        vo.setProjectId(75L);
        vo.setLeaderYn(false);
        projectMemberService.save(vo);
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
