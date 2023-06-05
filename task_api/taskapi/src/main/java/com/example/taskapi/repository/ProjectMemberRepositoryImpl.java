package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.domain.ProjectNameDto;
import com.example.taskapi.entity.ProjectMember;
import com.example.taskapi.entity.QProject;
import com.example.taskapi.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProjectMemberRepositoryImpl extends QuerydslRepositorySupport implements ProjectMemberRepositoryCustom {

    public ProjectMemberRepositoryImpl() {
        super(ProjectMember.class);
    }

    @Override
    public List<MemberDto> findMembersByProjectId(Integer projectId) {
        QProjectMember qProjectMember = QProjectMember.projectMember;

        return from(qProjectMember)
                .where(qProjectMember.project.projectId.eq(projectId))
                .select(Projections.constructor(MemberDto.class, qProjectMember.member.memberId, qProjectMember.role))
                .fetch();
    }

    /**
     * select PM.member_id,P.name from ProjectMembers PM
     * inner join Projects P on PM.project_id = P.project_id
     * where PM.member_id = 'nikki';
     * @param memberId
     * @return
     */
    @Override
    public List<ProjectNameDto> findProjectNamesByMemberId(String memberId) {
        QProjectMember qProjectMember = QProjectMember.projectMember;
        QProject qProject = QProject.project;

        return from(qProjectMember)
                .innerJoin(qProject)
                .on(qProjectMember.project.projectId.eq(qProject.projectId))
                .where(qProjectMember.member.memberId.eq(memberId))
                .select(Projections.constructor(ProjectNameDto.class, qProject.name))
                .fetch();

    }
}
