package com.project.ddbb.controller;

import com.project.ddbb.domain.service.CommentService;
import com.project.ddbb.domain.vo.CommentVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 코멘트 저장
     * @param commentVO
     * @param redirect
     * @return
     */
    @PostMapping("/commentSave")
    public String commentSave(CommentVO commentVO, RedirectAttributes redirect)
    {
        Long projectId = commentVO.getProjectId(); // 프로젝트 id 가져오기

        commentService.saveComment(commentVO); // 코멘트 저장

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info"; // 코멘트 저장 후 프로젝트 화면
    }

    /**
     * 코멘트 조회
     * @param projectId
     * @param model
     * @return
     */
    @PostMapping("/commentRead")
    @ResponseBody
    public String commentRead(@RequestParam Long projectId, Model model, RedirectAttributes redirect)
    {
        List<CommentVO> commentList = commentService.readComment(projectId); // 해당 프로젝트의 코멘트 조회 후 리스트로 저장

        model.addAttribute("commentList", commentList);

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info"; // 코멘트 조회 후 프로젝트 화면
    }

    /**
     * 코멘트 수정
     * @param commentVO
     * @param redirect
     * @return
     */
    @PostMapping("/commentUpdate")
    public String commentUpdate(CommentVO commentVO, RedirectAttributes redirect)
    {
        Long projectId = commentVO.getProjectId(); // 프로젝트 id 가져오기

        commentService.updateComment(commentVO); // 코멘트 수정

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info"; // 코멘트 수정 후 프로젝트 화면
    }

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
