package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.CodeCompareMapper;
import com.project.ddbb.domain.vo.CodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodeCompareService {
    private final CodeCompareMapper codeCompareMapper;

    /**
     * 코드 저장
     * @param params - 코드 정보
     */
    @Transactional
    public void saveCode(final CodeVO params) {
        codeCompareMapper.save(params);
    }

    /**
     * 코드 조회
     * @param PI - 현재 프로젝트 Id
     * @return 현재 프로젝트 코드 정보
     */
    @Transactional
    public CodeVO FindById(Long PI) {
        return codeCompareMapper.FindById(PI);
    }
}