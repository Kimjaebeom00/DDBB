package com.project.ddbb.controller;

import com.project.ddbb.domain.service.CodeCompareService;
import com.project.ddbb.domain.vo.CodeVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class CodeCompareController {

    private final CodeCompareService codeCompareService;


    /**
     * 코드 등록 화면
     * @param projectId
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/codeInsertPage")
    public String insertCode(@RequestParam Long projectId, Model model, HttpServletRequest request) {
        model.addAttribute("isLnb", false);
        model.addAttribute("projectId", projectId);

        return "layout/project/insert_code";
    }

    /**
     * 코드 저장
     * void
     */
    @PostMapping("/codeSave")
    public String codeSave(CodeVO codeVO, RedirectAttributes redirect, HttpServletRequest request) {
        Long projectId = codeVO.getProjectId(); // projectId 가져오기
        String codeTitle = codeVO.getTitle();  // 파일 제목 가져오기
        String currentCode = codeVO.getCurrentCode();  // 현재 저장할 코드 가져오기

        CodeVO cvo = codeCompareService.FindById(projectId);
        if (cvo == null) // 프로젝트에 코드가 없을 경우
        {
            cvo = new CodeVO();
            cvo.setProjectId(projectId);
            cvo.setTitle(codeTitle);
            cvo.setBeforeCode(currentCode);
            cvo.setCurrentCode(null);
            codeCompareService.saveCode(cvo);
        }
        else if (cvo.getCurrentCode() == null) // 프로젝트에 이전 코드만 있고, 현재 코드는 없을 경우
        {
            cvo.setCurrentCode(currentCode);
            codeCompareService.updateCode(cvo);
        }
        else // 프로젝트에 이전, 현재 코드가 모두 있는 경우
        {
            cvo.setBeforeCode(cvo.getCurrentCode());
            cvo.setCurrentCode(currentCode);
            codeCompareService.updateCode(cvo);
        }

        redirect.addAttribute("projectId", projectId);
        return "redirect:/project/info";
    }

    /**
     * 코드 비교
     * @return project/info
     */
    @PostMapping("/codeCompare")
    public String codeCompare(CodeVO codeVO, Model model) {
        Long projectId = codeVO.getProjectId(); // projectId 가져오기
        CodeVO cvo = codeCompareService.FindById(projectId); // db정보 확인

        List<String> beforeList = Arrays.asList(cvo.getBeforeCode().split("\n")); // 이전 코드
        List<String> currentList = Arrays.asList(cvo.getCurrentCode().split("\n"));  // 현재 코드

        Map<String, List<Integer>> indexMap = new HashMap<>();  // 수정, 추가 인덱스 정보 맵
        for (int i = 0; (i < beforeList.size()) && (i < currentList.size()); i++)  // 수정된 사항 체크
        {
            String beforeLine = beforeList.get(i);
            String currentLine = currentList.get(i);
            if (!beforeLine.equals(currentLine)) {
                List<Integer> modifiedLines = indexMap.getOrDefault("수정", new ArrayList<>());
                modifiedLines.add(i);
                indexMap.put("수정", modifiedLines);
            }
        }
        if (currentList.size() > beforeList.size())  // 현재 코드가 이전 코드보다 길 때 (추가 사항 체크)
        {
            List<Integer> addedLines = indexMap.getOrDefault("추가", new ArrayList<>());
            for (int i = beforeList.size(); i < currentList.size(); i++) {
                addedLines.add(i);
            }
            indexMap.put("추가", addedLines);
        }

        model.addAttribute("", cvo.getProjectId()); // Project Id
        model.addAttribute("", cvo.getTitle()); // Project Title
        model.addAttribute("", cvo.getBeforeCode()); // Before Code
        model.addAttribute("", cvo.getCurrentCode()); // Null or "" or Current Code
        model.addAttribute("", indexMap); // 수정, 추가 된 인덱스 정보 맵

        return "project/info";
    }
}
