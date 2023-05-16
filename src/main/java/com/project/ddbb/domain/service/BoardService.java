package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.BoardMapper;
import com.project.ddbb.domain.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    /**
     * 코멘트 저장
     * @param params - 코멘트 정보
     */
    @Transactional
    public void saveBoard(final BoardVO params)
    {
        boardMapper.save(params);
    }

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public List<BoardVO> findAllBoard()
    {
        return boardMapper.findAll();
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    @Transactional
    public void updateBoard(final BoardVO params)
    {
        boardMapper.update(params);
    }

    /**
     * 게시글 삭제
     * @param params - 게시글 정보
     */
    public void deleteBoard(final Long commentId)
    {
        boardMapper.deleteById(commentId);
    }
}
