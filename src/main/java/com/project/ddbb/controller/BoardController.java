package com.project.ddbb.controller;

import com.project.ddbb.domain.service.BoardService;
import com.project.ddbb.domain.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 작성 페이지
    @GetMapping("/board/write.do")
    public String openPostWrite(Model model) {
        String title = "제목",
                content = "내용",
                writer = "홍길동";

        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("writer", writer);

        return "board/write";
    }

    @GetMapping("/board/list.do")
    public String openBoardList(Model model) {
        List<BoardVO> boardList = boardService.findAllBoard();

        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    @PostMapping("/board/detail.do")
    public String openBoardDetail(@RequestParam("id") Long id, Model model) {
        BoardVO boardDetail = boardService.findBoardById(id);

        System.out.println(boardDetail.getId());

        model.addAttribute("boardDetail", boardDetail);
        return "board/detail";
    }
}
