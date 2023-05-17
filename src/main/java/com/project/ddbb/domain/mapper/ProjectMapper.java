package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    /**
     * 사용자가 참여한 프로젝트 목록 조회
     *
     * @param userId
     * @return
     */
    List<ProjectVO> findProjectsByUserId(Long userId);

    /**
     * 프로젝트 상세정보 조회
     * @param projectId
     * @return
     */
    ProjectVO findByProjectId(Long projectId);

    /**
     * 프로젝트 리더정보 조회
     * @param leaderYn
     * @return
     */
    ProjectVO findLeader_in(int leaderYn);


    /**
     * 프로젝트 등록
     * @param vo
     */
    void save(ProjectVO vo);

    /**
     * 프로젝트 수정
     * @param vo
     */
    void update(ProjectVO vo);

    /**
     * 프로젝트 삭제
     * @param projectId
     */
    void deleteById(Long projectId);


}