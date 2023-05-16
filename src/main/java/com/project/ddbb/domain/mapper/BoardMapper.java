package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.BoardVO;
import com.project.ddbb.domain.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BoardMapper {

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     */
    void save(BoardVO params);

    /**
     * 게시글 리스트 조회
     */
    List<BoardVO> findAll();

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    void update(BoardVO params);

    /**
     * 게시글 삭제
     * @param commentId - PK
     */
    void deleteById(Long commentId);
}
