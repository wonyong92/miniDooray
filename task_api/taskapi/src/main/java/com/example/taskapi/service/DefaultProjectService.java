package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    @Override
    public List<ProjectDto> findAllProjectDto() {
        return null;
    }

    @Override
    public ProjectDto findProjectDtoById(Integer projectId) {
        return null;
    }

    @Override
    public List<MemberDto> findAllMembersById(Integer projectId) {
        return projectMemberRepository.findMembersByProjectId(projectId);
    }

    @Override
    public ProjNameForMemDto findProjNamesByMemberId(String memberId) {
        boolean isExistedMember = memberRepository.existsById(memberId);
        if (!isExistedMember) {
            throw new NotFoundException("member not found, memberId = " + memberId);
        }

        List<ProjectNameDto> projectNamesByMemberId = projectMemberRepository.findProjectNamesByMemberId(memberId);
        return new ProjNameForMemDto(memberId, projectNamesByMemberId);
    }
}
