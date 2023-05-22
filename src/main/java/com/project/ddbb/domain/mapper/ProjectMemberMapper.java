package com.project.ddbb.domain.mapper;

import com.project.ddbb.domain.vo.ProjectMemberVO;
import com.project.ddbb.domain.vo.ProjectVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectMemberMapper {
    /**
     * 프로젝트 참여자 조회
     * @param projectId
     * @return
     */

    @MapKey("name")
    List<Map<String, Object>> findByProjectId(Long projectId);

    /**
     * 프로젝트 참여자 등록
     * @param vo
     */
    void save(ProjectMemberVO pmv);

    /**
     * 프로젝트 참여자 삭제
     * @param vo
     */
    void delete(ProjectMemberVO vo);

    /**
     * 현재 프로젝트에 참여하고 있는 멤버인지 확인
     * @param vo
     * @return
     */
    boolean checkProjectMember(ProjectMemberVO vo);
}
