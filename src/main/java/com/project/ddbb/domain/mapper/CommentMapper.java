package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommentMapper {

    /**
     * 코멘트 저장
     * @param params - 코멘트 정보
     */
    void save(CommentVO params);

    /**
     * 코멘트 조회
     * @param memberId
     * @param projectId
     * @return
     */
    List<CommentVO> readById(Long memberId, Long projectId);

    /**
     * 코멘트 수정
     * @param params - 코멘트 정보
     */
    void update(CommentVO params);

    /**
     * 코멘트 삭제
     * @param commentId - PK
     */
    void deleteById(Long commentId);
}
