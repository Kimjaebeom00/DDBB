package com.project.ddbb.controller;

import com.project.ddbb.domain.service.CommentService;
import com.project.ddbb.domain.vo.CommentVO;
import com.project.ddbb.domain.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 코멘트 저장 화면
     * @return
     */
    @PostMapping("/commentSavePage")
    public String commentSavePage(@RequestParam Long projectId, Model model, HttpServletRequest request)
    {
        model.addAttribute("isLnb", false);
        model.addAttribute("projectId", projectId);
        return "layout/comment/add";
    }
    /**
     * 코멘트 수정 화면
     * @return
     */
    @PostMapping("/commentUpdatePage")
    public String commentUpdatePage(@RequestParam Long projectId, Long commentId, Model model, HttpServletRequest request)
    {
        model.addAttribute("isLnb", false);
        model.addAttribute("projectId", projectId);
        model.addAttribute("commentId", commentId);


        return "layout/comment/modify";
    }

    /**
     * 코멘트 저장
     * @param commentVO
     * @param redirect
     * @return
     */
    @PostMapping("/commentSave")
    public String commentSave(CommentVO commentVO, RedirectAttributes redirect, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
        commentVO.setMemberId(memberInfo.getMemberId());
        commentService.saveComment(commentVO); // 코멘트 저장
        redirect.addAttribute("projectId", commentVO.getProjectId());

        return "redirect:/project/info"; // 코멘트 저장 후 프로젝트 화면
    }


    /**
     * 코멘트 수정
     * @param commentVO
     * @param redirect
     * @return
     */
    @PostMapping("/commentUpdate")
    public String commentUpdate(CommentVO commentVO, RedirectAttributes redirect) {
        Long projectId = commentVO.getProjectId(); // 프로젝트 id 가져오기
        commentService.updateComment(commentVO); // 코멘트 수정

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info"; // 코멘트 수정 후 프로젝트 화면
    }
//    /**
//     * 코멘트 수정
//     * @param commentVO
//     * @param redirect
//     * @return
//     */
//    @PostMapping("/commentUpdate")
//    public String commentUpdate(Long memberId, Long commentId, String editText, RedirectAttributes redirect) {
//        CommentVO commentVO = new CommentVO();
//        System.out.println("@@@@@@@@@@@commentUpdate 실행@@@@@@@@@@@");
//        System.out.println("MemberID : " + memberId);
//        System.out.println("CommentID : " + commentId);
//        System.out.println("editText : " + editText);
//        commentVO.setMemberId(memberId);
//        commentVO.setCommentId(commentId);
//        commentVO.setContent(editText);
//        Long projectId = commentVO.getProjectId(); // 프로젝트 id 가져오기
//
//        commentService.updateComment(commentVO); // 코멘트 수정
//
//        redirect.addFlashAttribute("projectId", projectId);
//        return "redirect:/project/info"; // 코멘트 수정 후 프로젝트 화면
//    }


    /**
     * 코멘트 삭제
     * @param commentVO
     * @param redirect
     * @return
     */
    @PostMapping("/commentDelete")
    public String commentDelete(CommentVO commentVO, RedirectAttributes redirect)
    {
        Long projectId = commentVO.getProjectId(); // 프로젝트 id 가져오기
        Long commentId = commentVO.getCommentId(); // 코멘트 id 가져오기
        commentService.deleteComment(commentId); // 코멘트 삭제

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info"; // 코멘트 삭제 후 프로젝트 화면
    }

}
