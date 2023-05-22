package com.project.ddbb.controller;

import com.project.ddbb.domain.CodeCompare.Diff;
import com.project.ddbb.domain.service.CodeCompareService;
import com.project.ddbb.domain.vo.CodeVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public Map<String, Object> codeCompare(@RequestParam Long projectId, Model model) {
        Map<String, Object> comparedCodeInfoMap = new HashMap<String, Object>(); // 전체 정보 데이터 맵

        Map<String, Map<String, List<Integer>>> codeMap = new HashMap<>(); // 이전코드, 현재코드 매핑 맵
        Map<String, List<Integer>> beforeMap = new HashMap<>(); // 이전코드 - 수정, 삭제, 추가 인덱스 정보 매핑 맵
        Map<String, List<Integer>> currentMap = new HashMap<>(); // 현재코드 - 수정, 삭제, 추가 인덱스 정보 매핑 맵

        if (codeCompareService.FindById(projectId) != null)
        {
            CodeVO cvo = codeCompareService.FindById(projectId); // db정보 확인

            String beforeCode = "";
            String currentCode = "";
            List<String> beforeList = new ArrayList<>();
            List<String> currentList = new ArrayList<>();

            if (cvo.getBeforeCode() != null)
            {
                beforeCode = cvo.getBeforeCode(); // 이전코드 문자열
                beforeList = Arrays.asList(cvo.getBeforeCode().split("\n")); // 이전 코드 라인별 리스트
            }
            if (cvo.getCurrentCode() != null)
            {
                currentCode = cvo.getCurrentCode(); // 현재코드 문자열
                currentList = Arrays.asList(cvo.getCurrentCode().split("\n"));  // 현재 코드 라인별 리스트
            }

            if ((cvo.getBeforeCode() != null) || (cvo.getCurrentCode() != null))
            {
                Diff.Item[] diffs = Diff.DiffText(beforeCode, currentCode); // 코드 비교 알고리즘 실행
                for (Diff.Item diff : diffs)
                {
                    if (((diff.deletedA != 0) && (diff.insertedB != 0)) && (diff.deletedA <= diff.insertedB)) // 수정 or 수정+추가된 경우
                    {
                        if (diff.deletedA == diff.insertedB) // 수정
                        {
                            for (int i = diff.StartA; i < diff.StartA + diff.insertedB; i++)
                            {
                                List<Integer> modifiedLines = beforeMap.getOrDefault("modify", new ArrayList<>());
                                modifiedLines.add(i);
                                beforeMap.put("modify", modifiedLines);
                            }

                            for (int i = diff.StartB; i < diff.StartB + diff.insertedB; i++)
                            {
                                List<Integer> modifiedLines = currentMap.getOrDefault("modify", new ArrayList<>());
                                modifiedLines.add(i);
                                currentMap.put("modify", modifiedLines);
                            }
                        }
                        else // 수정+추가
                        {
                            int beforeCount = 0;
                            for (int i = diff.StartA; i < diff.StartA + diff.insertedB; i++)
                            {
                                if (beforeCount < diff.deletedA)
                                {
                                    List<Integer> modifiedLines = beforeMap.getOrDefault("modify", new ArrayList<>());
                                    modifiedLines.add(i);
                                    beforeMap.put("modify", modifiedLines);
                                } else
                                {
                                    List<Integer> addLines = beforeMap.getOrDefault("add", new ArrayList<>());
                                    addLines.add(i);
                                    beforeMap.put("add", addLines);
                                }
                                beforeCount++;
                            }

                            int currentCount = 0;
                            for (int i = diff.StartB; i < diff.StartB + diff.insertedB; i++)
                            {
                                if (currentCount < diff.deletedA)
                                {
                                    List<Integer> modifiedLines = currentMap.getOrDefault("modify", new ArrayList<>());
                                    modifiedLines.add(i);
                                    currentMap.put("modify", modifiedLines);
                                } else
                                {
                                    List<Integer> addLines = currentMap.getOrDefault("add", new ArrayList<>());
                                    addLines.add(i);
                                    currentMap.put("add", addLines);
                                }
                                currentCount++;
                            }
                        }
                    }
                    else if (diff.deletedA != 0 && diff.insertedB == 0) // 삭제
                    {
                        for (int i = diff.StartA; i < diff.StartA + diff.deletedA; i++)
                        {
                            List<Integer> deleteLines = beforeMap.getOrDefault("delete", new ArrayList<>());
                            deleteLines.add(i);
                            beforeMap.put("delete", deleteLines);
                        }

                        for (int i = diff.StartB; i < diff.StartB + diff.deletedA; i++)
                        {
                            List<Integer> deleteLines = currentMap.getOrDefault("delete", new ArrayList<>());
                            deleteLines.add(i);
                            currentMap.put("delete", deleteLines);
                        }
                    }
                    else if (diff.deletedA == 0 && diff.insertedB != 0) // 추가
                    {
                        for (int i = diff.StartA; i < diff.StartA + diff.insertedB; i++)
                        {
                            List<Integer> addLines = beforeMap.getOrDefault("add", new ArrayList<>());
                            addLines.add(i);
                            beforeMap.put("add", addLines);
                        }

                        for (int i = diff.StartB; i < diff.StartB + diff.insertedB; i++)
                        {
                            List<Integer> addLines = currentMap.getOrDefault("add", new ArrayList<>());
                            addLines.add(i);
                            currentMap.put("add", addLines);
                        }
                    }
                    else // 수정 or 수정+삭제된 경우
                    {
                        int beforeCount = 0;
                        for (int i = diff.StartA; i < diff.StartA + diff.deletedA; i++)
                        {
                            if (beforeCount < diff.insertedB)
                            {
                                List<Integer> modifiedLines = beforeMap.getOrDefault("modify", new ArrayList<>());
                                modifiedLines.add(i);
                                beforeMap.put("modify", modifiedLines);
                            }
                            else
                            {
                                List<Integer> deleteLines = beforeMap.getOrDefault("delete", new ArrayList<>());
                                deleteLines.add(i);
                                beforeMap.put("delete", deleteLines);
                            }
                            beforeCount++;
                        }

                        int currentCount = 0;
                        for (int i = diff.StartB; i < diff.StartB + diff.deletedA; i++)
                        {
                            if (currentCount < diff.insertedB)
                            {
                                List<Integer> modifiedLines = currentMap.getOrDefault("modify", new ArrayList<>());
                                modifiedLines.add(i);
                                currentMap.put("modify", modifiedLines);
                            }
                            else
                            {
                                List<Integer> deleteLines = currentMap.getOrDefault("delete", new ArrayList<>());
                                deleteLines.add(i);
                                currentMap.put("delete", deleteLines);
                            }
                            currentCount++;
                        }
                    }
                }
                codeMap.put("before", beforeMap);
                codeMap.put("current", currentMap);

                comparedCodeInfoMap.put("CodeVO", cvo);
                comparedCodeInfoMap.put("beforeList", beforeList);
                comparedCodeInfoMap.put("currentList", currentList);
                comparedCodeInfoMap.put("codeMap", codeMap);
            }
        }

        return comparedCodeInfoMap;
    }
}
