package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.ProjectMapper;
import com.project.ddbb.domain.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectMapper projectMapper;

    /**
     * 사용자가 참여한 프로젝트 목록 조회
     * @param userId
     * @return
     */
    public List<ProjectVO> findProjectsByUserId(Long userId) {
        return projectMapper.findProjectsByUserId(userId);
    }

    /**
     * 프로젝트 상세정보 조회
     * @param projectId
     * @return
     */
    public ProjectVO findByProjectId(Long projectId) {
        return projectMapper.findByProjectId(projectId);
    }

    /**
     * 프로젝트 등록
     * @param vo
     */
    public Long save(ProjectVO vo){
        projectMapper.save(vo);
        return vo.getProjectId();
    }

    /**
     * 프로젝트 수정
     * @param vo
     */
    public void update(ProjectVO vo) {
        projectMapper.update(vo);
    }

    /**
     * 프로젝트 삭제
     * @param projectId
     */
    public void deleteById(Long projectId) {
        projectMapper.deleteById(projectId);
    }


    /**
     * 프로젝트 리더자 조회
     * @param leaderYn
     * @return
     */

    public ProjectVO findLeader_in(int leaderYn) {
        return projectMapper.findLeader_in(leaderYn);
    }
}
