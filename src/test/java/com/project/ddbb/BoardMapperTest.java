package com.project.ddbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ddbb.domain.mapper.BoardMapper;
import com.project.ddbb.domain.vo.BoardVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardMapperTest {

    @Autowired
    BoardMapper boardMapper;

    @Test
    void save() {
        BoardVO params = new BoardVO();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("테스터");
        params.setNoticeYn(false);
        boardMapper.save(params);

        List<BoardVO> posts = boardMapper.findAll();
        System.out.println("전체 게시글 개수는 : " + posts.size() + "개입니다.");
    }

    @Test
    void findById() {
        BoardVO post = boardMapper.findById(1L);  // long Type
        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        // 1. 게시글 수정
        BoardVO params = new BoardVO();
        params.setId(1L);
        params.setTitle("이 DB는 제가 가져갑니다.");
        params.setContent("어쩔건데.");
        params.setWriter("괴도키드");
        params.setNoticeYn(true);
        boardMapper.update(params);

        // 2. 게시글 상세정보 조회
        BoardVO post = boardMapper.findById(1L);
        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        System.out.println("삭제 이전의 전체 게시글 개수는 : " + boardMapper.findAll().size() + "개입니다.");
        boardMapper.deleteById(1L);
        System.out.println("삭제 이후의 전체 게시글 개수는 : " + boardMapper.findAll().size() + "개입니다.");
    }

    @Test
    void cancel() {
        boardMapper.cancelByID(1L);
    }
}