package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.BoardVO;
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
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    BoardVO findById(Long id);

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    void update(BoardVO params);

    /**
     * 게시글 삭제
     * @param id - PK
     */
    void deleteById(Long id);

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    List<BoardVO> findAll();

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
//    int count();

    /**
     * 게시글 삭제 취소
     * @param id - PK
     */
    void cancelById(Long id);
}
