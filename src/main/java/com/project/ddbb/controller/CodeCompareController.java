package com.project.ddbb.controller;

import com.project.ddbb.domain.service.CodeCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CodeCompareController {
    private final CodeCompareService codeCompareService;

    // 게시글 작성 페이지
    @GetMapping("")
    public String openPostWrite(Model model) {
        String title = "제목",
                content = "내용",
                writer = "홍길동";

        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("writer", writer);

        return "hi";
    }

}
