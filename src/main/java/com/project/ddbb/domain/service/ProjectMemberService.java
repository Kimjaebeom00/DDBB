package com.project.ddbb.domain.service;

import com.project.ddbb.domain.mapper.ProjectMemberMapper;
import com.project.ddbb.domain.vo.ProjectMemberVO;
import com.project.ddbb.domain.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {
    private final ProjectMemberMapper projectMemberMapper;

    /**
     * 프로젝트 참여자 조회
     * @param projectId
     * @return
     */
    public List<Map<String, Object>> findByProjectId(Long projectId) {
        return projectMemberMapper.findByProjectId(projectId);
    }

    /**
     * 프로젝트 참여자 등록
     * @param pmv
     */
    public void save(ProjectMemberVO pmv){
        projectMemberMapper.save(pmv);
    }

    /**
     * 프로젝트 참여자 삭제
     * @param vo
     */
    public void delete(ProjectMemberVO vo) {
        projectMemberMapper.delete(vo);
    }

    /**
     * 현재 프로젝트에 참여하고 있는 멤버인지 확인
     * @param vo
     * @return
     */
    public boolean checkProjectMember(ProjectMemberVO vo) {
        return projectMemberMapper.checkProjectMember(vo);
    }
}
