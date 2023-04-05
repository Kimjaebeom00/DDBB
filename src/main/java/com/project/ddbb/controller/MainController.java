package com.project.ddbb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    /**
     * 메인화면
     * @return
     */
    @GetMapping("/")
    public String main() {

        return "auth/sign_in";
    }
}
