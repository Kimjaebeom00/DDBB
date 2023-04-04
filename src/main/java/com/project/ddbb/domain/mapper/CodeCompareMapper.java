package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.CodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CodeCompareMapper {
    /**
     * 코드 저장
     * @param params - 코드 정보
     */
    void save(CodeVO params);

    /**
     * 코드 업데이트
     * @param params - 코드 정보
     */
    void update(CodeVO params);

    /**
     * 현재 프로젝트 코드 조회
     * @return 현재 프로젝트 코드 정보
     */
    CodeVO FindById(Long id);
}
