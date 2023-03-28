package com.project.ddbb;

import com.project.ddbb.domain.vo.BoardVO;
import com.project.ddbb.domain.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BoardService boardService;

    @Test
    void save() {
        BoardVO params = new BoardVO();
        params.setTitle("2번 게시글 제목");
        params.setContent("2번 게시글 내용");
        params.setWriter("김성렬");
        params.setNoticeYn(false);
        Long id = boardService.saveBoard(params);
        System.out.println(id);
    }

}
