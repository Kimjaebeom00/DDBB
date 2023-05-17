package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.ProjectMemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMemberMapper {
    /**
     * 프로젝트 참여자 조회
     * @param projectId
     * @return
     */
    List<ProjectMemberVO> findByProjectId(Long projectId);

    /**
     * 프로젝트 참여자 등록
     * @param vo
     */
    void save(ProjectMemberVO vo);

    /**
     * 프로젝트 참여자 삭제
     * @param vo
     */
    void delete(ProjectMemberVO vo);

    /**
     * 프로젝트 참여자 조회
     * @param leaderYn
     * @return
     */
    List<ProjectMemberVO> findLeader_in(int leaderYn);
}
