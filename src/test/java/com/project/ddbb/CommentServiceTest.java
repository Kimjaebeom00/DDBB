package com.project.ddbb;

import com.project.ddbb.controller.CommentController;
import com.project.ddbb.domain.service.CommentService;
import com.project.ddbb.domain.vo.CommentVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    void commentUpdate(){
        CommentVO commentVO = new CommentVO();
        commentVO.setCommentId(5L);
        commentVO.setProjectId(75L);
        commentVO.setMemberId(16L);
        commentVO.setContent("왜 안되는건데");
        commentVO.setUpdatedDate(LocalDateTime.now());
        commentVO.setCreatedDate(LocalDateTime.now());
        commentVO.setStatus(0);
        commentService.updateComment(commentVO);
    }
}
