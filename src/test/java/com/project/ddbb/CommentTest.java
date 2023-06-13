package com.project.ddbb;

import com.project.ddbb.controller.CommentController;
import com.project.ddbb.domain.service.CommentService;
import com.project.ddbb.domain.vo.CommentVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
public class CommentTest {
    CommentController commentController;
    CommentService commentService;

    @Test
    void commentUpdate(){
        CommentVO commentVO = new CommentVO();
        commentVO.setCommentId(5L);
        commentVO.setProjectId(75L);
        commentVO.setMemberId(16L);
        commentVO.setContent("왜 안돼는건데");
        commentVO.setUpdatedDate(LocalDateTime.now());
        commentVO.setCreatedDate(LocalDateTime.now());
        commentVO.setStatus(0);
        commentService.updateComment(commentVO);
    }
}
