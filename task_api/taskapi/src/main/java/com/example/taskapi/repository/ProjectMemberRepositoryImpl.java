package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.entity.ProjectMember;
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
}
