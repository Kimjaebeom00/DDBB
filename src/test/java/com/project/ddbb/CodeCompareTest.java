package com.project.ddbb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddbb.domain.vo.TestCode;
import com.project.ddbb.domain.vo.CodeVO;
import com.project.ddbb.domain.service.CodeCompareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

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
            cvo.setCurrentCode("Final Contents");
            codeService.updateCode(cvo);
        }
    }
}
