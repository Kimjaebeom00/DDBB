package com.project.ddbb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddbb.domain.vo.TestCode;
import com.project.ddbb.domain.vo.CodeVO;
import com.project.ddbb.domain.service.CodeCompareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.*;

@SpringBootTest
public class CodeCompareTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CodeCompareService codeService;

    Long testCurrentProjectId = 1L;

    @Test
    void save()
    {
        CodeVO cvo = codeService.FindById(testCurrentProjectId);
        TestCode tc = new TestCode();
        if (cvo == null) // 프로젝트에 코드가 없을 경우
        {
            cvo = new CodeVO();
            cvo.setCodeId(1L);
            cvo.setProjectId(testCurrentProjectId);
            cvo.setTitle("DDBB");
            cvo.setBeforeCode(tc.getTestCode1());
            cvo.setCurrentCode(null);
            codeService.saveCode(cvo);
        }
        else if (cvo.getCurrentCode() == null) // 프로젝트에 이전 코드만 있고, 현재 코드는 없을 경우
        {
            cvo.setCurrentCode(tc.getTestCode2());
            codeService.updateCode(cvo);
        }
        else // 프로젝트에 이전, 현재 코드가 모두 있는 경우
        {
            cvo.setBeforeCode(cvo.getCurrentCode());
            cvo.setCurrentCode(tc.getTestCode3());
            codeService.updateCode(cvo);
        }
    }

    @Test
    void Compare() {
        CodeVO cvo = codeService.FindById(testCurrentProjectId); // db정보 확인

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

        System.out.println(cvo.getProjectId());
        System.out.println(cvo.getTitle());
        System.out.println(cvo.getBeforeCode());
        System.out.println(cvo.getCurrentCode());
        System.out.println(indexMap);
    }
}
