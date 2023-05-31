package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.CommentMapper;
import com.project.ddbb.domain.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    /**
     * 코멘트 저장
     * @param params - 코멘트 정보
     */
    @Transactional
    public void saveComment(final CommentVO params)
    {
        commentMapper.save(params);
    }

    /**
     * 코멘트 조회
     * @param memberId
     * @param projectId
     * @return
     */
    public List<CommentVO> readComment(final Long memberId, final Long projectId)
    {
        return commentMapper.readById(memberId, projectId);
    }

    /**
     * 코멘트 수정
     * @param params - 코멘트 정보
     */
    @Transactional
    public void updateComment(final CommentVO params)
    {
        commentMapper.update(params);
    }

    /**
     * 코멘트 삭제
     * @param commentId - 코멘트 ID
     */
    public void deleteComment(final Long commentId)
    {
        commentMapper.deleteById(commentId);
    }
}
