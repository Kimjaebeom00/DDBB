package com.project.ddbb;

import com.project.ddbb.domain.vo.CodeCompareVO;
import com.project.ddbb.domain.service.CodeCompareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeCompareTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CodeCompareService codeService;
    CodeCompareVO ccvo;

    Long testCurrentProjectId = 1L;

    @Test
    void save() {
        ccvo = new CodeCompareVO();
        ccvo = codeService.FindById(testCurrentProjectId);

        if (ccvo.getBeforeCode() == null) // 프로젝트에 코드가 없을 경우
        {
            ccvo.setProjectId(testCurrentProjectId);
            ccvo.setTitle("DDBB");
            ccvo.setBeforeCode("Before Contents");
            ccvo.setCurrentCode(null);
            codeService.saveCode(ccvo);
        }
        else if (ccvo.getCurrentCode() == null) // 프로젝트에 이전 코드만 있고, 현재 코드는 없을 경우
        {
            ccvo.setProjectId(testCurrentProjectId);
            ccvo.setTitle(ccvo.getTitle());
            ccvo.setBeforeCode(ccvo.getBeforeCode());
            ccvo.setCurrentCode("Current Contents");
            codeService.saveCode(ccvo);
        }
        else // 프로젝트에 이전, 현재 코드가 모두 있는 경우
        {
            ccvo.setProjectId(testCurrentProjectId);
            ccvo.setTitle(ccvo.getTitle());
            ccvo.setBeforeCode(ccvo.getCurrentCode());
            ccvo.setCurrentCode("Final Contents");
            codeService.saveCode(ccvo);
        }
    }
}
