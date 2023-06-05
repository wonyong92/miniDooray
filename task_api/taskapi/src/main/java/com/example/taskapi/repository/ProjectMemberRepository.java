package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMember.Pk> {

//    select M.member_id, nickname, email from ProjectMembers
//    inner join Members M on ProjectMembers.member_id = M.member_id
//    where project_id = 1;
    @Query("SELECT new com.example.taskapi.domain.MemberDto(m.memberId, m.nickname, m.email)from ProjectMember PM inner join Member m" +
            " on PM.member.memberId = m.memberId where PM.project.projectId = ?1")
    List<MemberDto> findAllProjectMemberDtoByProjectId(Integer projectId);
}
