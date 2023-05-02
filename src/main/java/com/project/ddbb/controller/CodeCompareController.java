package com.project.ddbb.controller;

import com.project.ddbb.domain.service.CodeCompareService;
import com.project.ddbb.domain.vo.CodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class CodeCompareController {

    private final CodeCompareService codeCompareService;

    /**
     * 코드 저장
     * @return
     */
    @PostMapping("/codeSave")
    public String codeSave(CodeVO codeVO) {
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
        return "몰라몰라!! 뭘로주라고?!?!?!";
    }

    /**
     * 코드 비교
     * @return
     */
    @PostMapping("/codeCompare")
    public Map codeCompare(CodeVO codeVO) {
        Long projectId = codeVO.getProjectId(); // projectId 가져오기
        CodeVO cvo = codeCompareService.FindById(projectId); // db정보 확인
        List<String> beforeList = Arrays.asList(cvo.getBeforeCode().split("\n")); // 이전 코드
        List<String> currentList = Arrays.asList(cvo.getCurrentCode().split("\n"));  // 현재 코드

        Map<String, List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; (i < beforeList.size()) && (i < currentList.size()); i++) {
            String line1 = beforeList.get(i);
            String line2 = currentList.get(i);
            if (!line1.equals(line2)) {
                List<Integer> modifiedLines = indexMap.getOrDefault("수정", new ArrayList<>());
                modifiedLines.add(i);
                indexMap.put("수정", modifiedLines);
            }
        }
        if (currentList.size() > beforeList.size()) {
            List<Integer> addedLines = indexMap.getOrDefault("추가", new ArrayList<>());
            for (int i = beforeList.size(); i < currentList.size(); i++) {
                addedLines.add(i);
            }
            indexMap.put("추가", addedLines);
        }

        return indexMap;
    }
}
