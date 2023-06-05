package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.Task;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final TagRepository tagRepository;
    @Override
    public List<ProjectDetailDto> findAllProjectDto() {
        return null;
    }

    @Override
    public ProjectDetailDto findProjectDtoById(Integer projectId) {
        ProjectDto projectDto = projectRepository.findProjectDtoByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException("project not found projectId = " + projectId));
        List<MemberDto> projectMembers = projectMemberRepository.findMembersByProjectId(projectId);
        List<TaskDto> projectTasks = taskRepository.findByProject_ProjectId(projectId)
                .stream()
                .map(x -> new TaskDto(x.getTaskId(), x.getTitle(), x.getMember().getMemberId()))
                .collect(Collectors.toList());
        List<MilestoneDto> projectMilestones = milestoneRepository.findAllByProject_ProjectId(projectId)
                .stream()
                .map(x -> new MilestoneDto(x.getMilestoneId(), x.getName(), x.getStartAt(), x.getEndAt()))
                .collect(Collectors.toList());
        List<TagDto> projectTags = tagRepository.findAllByProject_ProjectId(projectId);

        return new ProjectDetailDto(projectDto.getProjectId(), projectDto.getName(), projectDto.getStatus(),
                projectMembers, projectMilestones, projectTags, projectTasks);
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
