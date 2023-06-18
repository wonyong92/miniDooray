package com.example.taskapi.controller;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.Project;
import com.example.taskapi.entity.ProjectMember;
import com.example.taskapi.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ProjectRestController.class})
class ProjectRestControllerTest {
    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void readProject() throws Exception {
        when(projectService.readProject(anyInt()))
                .thenReturn(new ProjectDetailReadResponseDto(
                        1
                        , "testProject"
                        , Project.Status.TERMINATION
                        , List.of(
                        new MemberReadResponseDto("tester1", ProjectMember.Role.ADMIN),
                        new MemberReadResponseDto("tester2", ProjectMember.Role.MEMBER))
                        , List.of(new MilestoneReadResponseDto(1, "testMilestone", LocalDate.now(), LocalDate.now()))
                        , List.of(new TagReadResponseDto(1, "testTag"))
                        , List.of(new TaskReadResponseDto(1, "testTitle", "tester"))));

        mockMvc.perform(get("/projects/{projectId}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.name").value("testProject"))
                .andExpect(jsonPath("$.status").value("TERMINATION"))
                .andExpect(jsonPath("$.members[0].memberId").value("tester1"));
    }

    @Test
    void readProjectMembers() throws Exception {
        when(projectService.readProjectMembers(anyInt()))
                .thenReturn(new ProjectMemberReadResponseDto
                        (1
                                , Project.Status.TERMINATION
                                , List.of(new MemberReadResponseDto("tester1", ProjectMember.Role.ADMIN)
                                , new MemberReadResponseDto("tester2", ProjectMember.Role.MEMBER))
                        )
                );
        mockMvc.perform(get("/projects/{projectId}/members", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.status").value("TERMINATION"))
                .andExpect(jsonPath("$.members[0].memberId").value("tester1"))
                .andExpect(jsonPath("$.members[0].role").value("ADMIN"));
    }

    @Test
    void readProjNameForMem() throws Exception {
        when(projectService.readProjNamesForMem(anyString()))
                .thenReturn(new ProjNameForMemReadResponseDto(
                        "tester1"
                        , List.of(new ProjectNameReadResponseDto("testProject"))));

        mockMvc.perform(get("/projects/members/{memberId}", "tester1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("tester1"))
                .andExpect(jsonPath("$.projectNames[0].name").value("testProject"));
    }

    @Test
    void createProject() throws Exception {
        ProjectCreateRequest projectCreateRequest = new ProjectCreateRequest("project", "tester1");
        when(projectService.createProject(any(ProjectCreateRequest.class)))
                .thenReturn(new ProjectCreateResponseDto("tester1", 1, "testProject", Project.Status.ACTIVATE));
        mockMvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.adminId").value("tester1"))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.name").value("testProject"))
                .andExpect(jsonPath("$.status").value("ACTIVATE"));

    }

    @Test
    void createProjectValidationFailed() throws Exception {
        ProjectCreateRequest invalidRequest = new ProjectCreateRequest(null, null);
        mockMvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProject() throws Exception {
        ProjectUpdateRequest projectUpdateRequest = new ProjectUpdateRequest("editProject", Project.Status.ACTIVATE);
        when(projectService.updateProject(any(ProjectUpdateRequest.class), anyInt()))
                .thenReturn(new ProjectUpdateResponseDto(1, projectUpdateRequest.getName(), projectUpdateRequest.getStatus()));

        mockMvc.perform(put("/projects/{projectId}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.name").value("editProject"))
                .andExpect(jsonPath("$.status").value("ACTIVATE"));
    }

    @Test
    void updateProjectValidationFailed() throws Exception {
        ProjectUpdateRequest invalidRequest = new ProjectUpdateRequest(null, null);
        mockMvc.perform(put("/projects/{projectId}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteProject() throws Exception {
        when(projectService.deleteProject(anyInt()))
                .thenReturn(new ProjectDeleteResponseDto(1));

        mockMvc.perform(delete("/projects/{projectId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1));

    }
}