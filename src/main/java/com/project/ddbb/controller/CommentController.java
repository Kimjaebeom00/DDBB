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
    @PostMapping("/comment/write")
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
    @PostMapping("/comment/list")
    @ResponseBody
    public String commentRead(@RequestParam Long projectId, Model model)
    {
        List<CommentVO> commentList = commentService.readComment(projectId); // 해당 프로젝트의 코멘트 조회 후 리스트로 저장

        model.addAttribute("commentList", commentList);

        return "comment/list";
    }

    /**
     * 코멘트 수정
     * @param commentVO
     * @param redirect
     * @return
     */
    @PostMapping("/comment/detail")
    public String commentUpdate(CommentVO commentVO, RedirectAttributes redirect)
    {
        Long projectId = commentVO.getProjectId(); // 프로젝트 id 가져오기

        commentService.updateComment(commentVO); // 코멘트 수정

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info"; // 코멘트 수정 후 프로젝트 화면
    }

//    /**
//     * 코멘트 삭제
//     * @param id
//     * @param model
//     * @return
//     */
//    @PostMapping("/board/detail")
//    public String commentDelete(@RequestParam("id") Long id, Model model) {
//        //BoardVO boardDetail = boardService.findBoardById(id);
//
//        //System.out.println(boardDetail.getId());
//
//        //model.addAttribute("boardDetail", boardDetail);
//        return "board/detail";
//    }
}
