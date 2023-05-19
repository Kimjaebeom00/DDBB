package com.project.ddbb;

import com.project.ddbb.domain.CodeCompare.Diff;
import com.project.ddbb.domain.CodeCompare.TestCode;
import com.project.ddbb.domain.vo.CodeVO;
import com.project.ddbb.domain.service.CodeCompareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.ArrayList;

@SpringBootTest
public class CodeCompareTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CodeCompareService codeService;

    Long testCurrentProjectId = 75L;

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

    @Test  // 기존 코드 비교 방식 - 라인으로 비교 함. 라인으로 봤을 땐 맞지만 깃허브처럼 안됨.
    void compareTest1() {
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

    @Test // 현재 코드 비교 방식 - 진짜 깃허브처럼 비교됨.
    void compareTest2()
    {
        Map<String, Map<String, List<Integer>>> codeMap = new HashMap<>(); // 이전코드, 현재코드 매핑 맵
        Map<String, List<Integer>> beforeMap = new HashMap<>(); // 이전코드 - 수정, 삭제, 추가 인덱스 정보 매핑 맵
        Map<String, List<Integer>> currentMap = new HashMap<>(); // 현재코드 - 수정, 삭제, 추가 인덱스 정보 매핑 맵

        CodeVO cvo = codeService.FindById(testCurrentProjectId); // db정보 확인
        String beforeCode = cvo.getBeforeCode(); // 이전코드
        String currentCode = cvo.getCurrentCode(); // 현재코드

        Diff.Item[] diffs = Diff.DiffText(beforeCode, currentCode);
        for (Diff.Item diff : diffs)
        {
            if ( ((diff.deletedA != 0) && (diff.insertedB != 0)) && (diff.deletedA <= diff.insertedB) ) // 수정 or 수정+추가된 경우
            {
                if (diff.deletedA == diff.insertedB) // 수정
                {
                    for (int i = diff.StartA; i < diff.StartA + diff.insertedB; i++)
                    {
                        List<Integer> modifiedLines = beforeMap.getOrDefault("수정", new ArrayList<>());
                        modifiedLines.add(i);
                        beforeMap.put("수정", modifiedLines);
                    }

                    for (int i = diff.StartB; i < diff.StartB + diff.insertedB; i++)
                    {
                        List<Integer> modifiedLines = currentMap.getOrDefault("수정", new ArrayList<>());
                        modifiedLines.add(i);
                        currentMap.put("수정", modifiedLines);
                    }
                }
                else // 수정+추가
                {
                    int beforeCount = 0;
                    for (int i = diff.StartA; i < diff.StartA + diff.insertedB; i++)
                    {
                        if (beforeCount < diff.deletedA)
                        {
                            List<Integer> modifiedLines = beforeMap.getOrDefault("수정", new ArrayList<>());
                            modifiedLines.add(i);
                            beforeMap.put("수정", modifiedLines);
                        }
                        else
                        {
                            List<Integer> addLines = beforeMap.getOrDefault("추가", new ArrayList<>());
                            addLines.add(i);
                            beforeMap.put("추가", addLines);
                        }
                        beforeCount++;
                    }

                    int currentCount = 0;
                    for (int i = diff.StartB; i < diff.StartB + diff.insertedB; i++)
                    {
                        if (currentCount < diff.deletedA)
                        {
                            List<Integer> modifiedLines = currentMap.getOrDefault("수정", new ArrayList<>());
                            modifiedLines.add(i);
                            currentMap.put("수정", modifiedLines);
                        }
                        else
                        {
                            List<Integer> addLines = currentMap.getOrDefault("추가", new ArrayList<>());
                            addLines.add(i);
                            currentMap.put("추가", addLines);
                        }
                        currentCount++;
                    }
                }
            }
            else if (diff.deletedA != 0 && diff.insertedB == 0) // 삭제
            {
                for (int i = diff.StartA; i < diff.StartA + diff.deletedA; i++)
                {
                    List<Integer> deleteLines = beforeMap.getOrDefault("삭제", new ArrayList<>());
                    deleteLines.add(i);
                    beforeMap.put("삭제", deleteLines);
                }

                for (int i = diff.StartB; i < diff.StartB + diff.deletedA; i++)
                {
                    List<Integer> deleteLines = currentMap.getOrDefault("삭제", new ArrayList<>());
                    deleteLines.add(i);
                    currentMap.put("삭제", deleteLines);
                }
            }
            else if (diff.deletedA == 0 && diff.insertedB != 0) // 추가
            {
                for (int i = diff.StartA; i < diff.StartA + diff.insertedB; i++)
                {
                    List<Integer> addLines = beforeMap.getOrDefault("추가", new ArrayList<>());
                    addLines.add(i);
                    beforeMap.put("추가", addLines);
                }

                for (int i = diff.StartB; i < diff.StartB + diff.insertedB; i++)
                {
                    List<Integer> addLines = currentMap.getOrDefault("추가", new ArrayList<>());
                    addLines.add(i);
                    currentMap.put("추가", addLines);
                }
            }
            else // 수정 or 수정+삭제된 경우
            {
                int beforeCount = 0;
                for (int i = diff.StartA; i < diff.StartA + diff.deletedA; i++)
                {
                    if (beforeCount < diff.insertedB)
                    {
                        List<Integer> modifiedLines = beforeMap.getOrDefault("수정", new ArrayList<>());
                        modifiedLines.add(i);
                        beforeMap.put("수정", modifiedLines);
                    }
                    else
                    {
                        List<Integer> deleteLines = beforeMap.getOrDefault("삭제", new ArrayList<>());
                        deleteLines.add(i);
                        beforeMap.put("삭제", deleteLines);
                    }
                    beforeCount++;
                }

                int currentCount = 0;
                for (int i = diff.StartB; i < diff.StartB + diff.deletedA; i++)
                {
                    if (currentCount < diff.insertedB)
                    {
                        List<Integer> modifiedLines = currentMap.getOrDefault("수정", new ArrayList<>());
                        modifiedLines.add(i);
                        currentMap.put("수정", modifiedLines);
                    }
                    else
                    {
                        List<Integer> deleteLines = currentMap.getOrDefault("삭제", new ArrayList<>());
                        deleteLines.add(i);
                        currentMap.put("삭제", deleteLines);
                    }
                    currentCount++;
                }
            }

            System.out.println("StartA: " + diff.StartA);
            System.out.println("DeletedA: " + diff.deletedA);
            System.out.println("StartB: " + diff.StartB);
            System.out.println("InsertedB: " + diff.insertedB);
            System.out.println();
        }
        codeMap.put("이전",beforeMap);
        codeMap.put("현재",currentMap);

        System.out.println("이전 = " + codeMap.getOrDefault("이전", beforeMap));
        System.out.println("현재 = " + codeMap.getOrDefault("현재", currentMap));
        System.out.println(codeMap);
    }
}