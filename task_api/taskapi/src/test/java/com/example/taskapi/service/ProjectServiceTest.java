package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.*;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @InjectMocks
    private DefaultProjectService projectService;


    @Test
    void createProject() {
        ProjectCreateRequest request = new ProjectCreateRequest("testProject", "admin");
        Member admin = new Member();
        ReflectionTestUtils.setField(admin, "memberId", "admin");
        Project project = new Project(request.getName(), Project.Status.ACTIVATE);
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(admin));
        when(projectRepository.save(any(Project.class)))
                .thenReturn(project);
        when(projectMemberRepository.save(any(ProjectMember.class)))
                .thenReturn(mock(ProjectMember.class));
        ProjectCreateResponseDto actual = projectService.createProject(request);
        Assertions.assertThat(actual.getAdminId()).isEqualTo(admin.getMemberId());
        Assertions.assertThat(actual.getProjectId()).isEqualTo(project.getProjectId());
        Assertions.assertThat(actual.getStatus()).isEqualTo(Project.Status.ACTIVATE);
        Assertions.assertThat(actual.getName()).isEqualTo(request.getName());
    }

    @Test
    void createNotFoundAdminProject() {
        when(memberRepository.findById(any()))
                .thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> projectService.createProject(mock(ProjectCreateRequest.class)));
    }

    @Test
    void updateProject() {
        ProjectUpdateRequest updateRequest = new ProjectUpdateRequest("update project", Project.Status.ACTIVATE);
        Project project = new Project();
        ReflectionTestUtils.setField(project, "projectId", 1);
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));
        ProjectUpdateResponseDto actual = projectService.updateProject(updateRequest, 1);
        Assertions.assertThat(actual.getProjectId()).isEqualTo(project.getProjectId());
        Assertions.assertThat(actual.getName()).isEqualTo(project.getName());
        Assertions.assertThat(actual.getStatus()).isEqualTo(project.getStatus());
    }

    @Test
    void updateNotFoundProject() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> projectService.updateProject(mock(ProjectUpdateRequest.class), 12123))
                .isInstanceOf(NotFoundException.class);
        verify(projectRepository, atMostOnce()).findById(any());
    }

    @Test
    void deleteProject() {
        Project project = new Project();
        ReflectionTestUtils.setField(project, "projectId", 1);
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));
        ProjectDeleteResponseDto actual = projectService.deleteProject(1);
        Assertions.assertThat(actual.getProjectId()).isEqualTo(project.getProjectId());
    }

    @Test
    void deleteNotFoundProject() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> projectService.deleteProject(12121))
                .isInstanceOf(NotFoundException.class);
        verify(projectRepository, atMostOnce()).findById(any());
    }

    @Test
    void readProject() {
        Project project = new Project();
        ReflectionTestUtils.setField(project, "projectId", 1);
        ReflectionTestUtils.setField(project, "name", "testProject");
        ReflectionTestUtils.setField(project, "status", Project.Status.TERMINATION);
        when(projectRepository.findProjectDtoByProjectId(anyInt()))
                .thenReturn(Optional.of(
                        new ProjectReadResponseDto() {
                            @Override
                            public Integer getProjectId() {
                                return project.getProjectId();
                            }
                            @Override
                            public String getName() {
                                return project.getName();
                            }
                            @Override
                            public Project.Status getStatus() {
                                return project.getStatus();
                            }
                        }
                ));
        when(projectMemberRepository.findMembersByProjectId(anyInt()))
                .thenReturn(List.of(
                        new MemberReadResponseDto("tester1", ProjectMember.Role.ADMIN),
                        new MemberReadResponseDto("tester2", ProjectMember.Role.MEMBER),
                        new MemberReadResponseDto("tester2", ProjectMember.Role.MEMBER)
                ));
        Member writer = new Member();
        ReflectionTestUtils.setField(writer, "memberId", "tester1");
        Task task = new Task("test", "testContent", LocalDateTime.now(), LocalDateTime.now(), writer, project);
        when(taskRepository.findByProject_ProjectId(anyInt()))
                .thenReturn(List.of(
                        task
                ));
        Milestone milestone = new Milestone("testMilestone", LocalDate.now(), LocalDate.now(), project);
        when(milestoneRepository.findAllByProject_ProjectId(anyInt()))
                .thenReturn(List.of(milestone));
        when(tagRepository.findAllByProject_ProjectId(anyInt()))
                .thenReturn(List.of(
                        new TagReadResponseDto(1, "testTag")
                ));
        ProjectDetailReadResponseDto actual = projectService.readProject(1);
        Assertions.assertThat(actual.getProjectId()).isEqualTo(project.getProjectId());
        Assertions.assertThat(actual.getName()).isEqualTo(project.getName());
        Assertions.assertThat(actual.getStatus()).isEqualTo(project.getStatus());
        Assertions.assertThat(actual.getMembers()).hasSize(3);
        Assertions.assertThat(actual.getMilestones()).hasSize(1);
        Assertions.assertThat(actual.getTags()).hasSize(1);
        Assertions.assertThat(actual.getTasks()).hasSize(1);
    }

    @Test
    void readNotFoundProject() {

        when(projectRepository.findProjectDtoByProjectId(any()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> projectService.readProject(11111))
                .isInstanceOf(NotFoundException.class);
        verify(projectRepository, atMostOnce()).findProjectDtoByProjectId(any());

    }

    @Test
    void readProjectMembers() {
        Project project = new Project("testProject", Project.Status.TERMINATION);
        ReflectionTestUtils.setField(project, "projectId", 1);
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));
        when(projectMemberRepository.findMembersByProjectId(anyInt()))
                .thenReturn(List.of(
                        new MemberReadResponseDto("tester1", ProjectMember.Role.ADMIN),
                        new MemberReadResponseDto("tester2", ProjectMember.Role.MEMBER),
                        new MemberReadResponseDto("tester2", ProjectMember.Role.MEMBER)
                ));
        ProjectMemberReadResponseDto actual = projectService.readProjectMembers(1);
        Assertions.assertThat(actual.getProjectId()).isEqualTo(project.getProjectId());
        Assertions.assertThat(actual.getStatus()).isEqualTo(project.getStatus());
        Assertions.assertThat(actual.getMembers()).hasSize(3);
    }

    @Test
    void readNotFoundProjectMembers() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> projectService.readProjectMembers(11111))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void readProjNamesForMem() {
        when(memberRepository.existsById(anyString()))
                .thenReturn(true);
        when(projectMemberRepository.findProjectNamesByMemberId(anyString()))
                .thenReturn(List.of(new ProjectNameReadResponseDto("testProject"),
                        new ProjectNameReadResponseDto("testProject2")));
        ProjNameForMemReadResponseDto actual = projectService.readProjNamesForMem("tester");
        Assertions.assertThat(actual.getMemberId()).isEqualTo("tester");
        Assertions.assertThat(actual.getProjectNames()).hasSize(2);
    }

    @Test
    void readNotFoundProjNamesForMem() {
        when(memberRepository.existsById(anyString()))
                .thenReturn(false);
        Assertions.assertThatThrownBy(() -> projectService.readProjNamesForMem("XXX"))
                .isInstanceOf(NotFoundException.class);
    }
}