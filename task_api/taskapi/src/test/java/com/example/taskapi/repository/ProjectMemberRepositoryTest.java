package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberReadResponseDto;
import com.example.taskapi.domain.ProjectNameReadResponseDto;
import com.example.taskapi.entity.ProjectMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProjectMemberRepositoryTest {

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Test
    void findAll() {
        List<ProjectMember> actual = projectMemberRepository.findAll();
        for (ProjectMember projectMember : actual) {
            System.out.println(projectMember);
        }
        Assertions.assertThat(actual).isNotEmpty();
    }

    @Test
    void findMembersByProjectId() {
        List<MemberReadResponseDto> projectMembers = projectMemberRepository.findMembersByProjectId(1);
        for (MemberReadResponseDto projectMember : projectMembers) {
            System.out.println(projectMember);
        }
        Assertions.assertThat(projectMembers).isNotEmpty();
    }

    @Test
    void findProjectNamesByMemberId() {
        List<ProjectNameReadResponseDto> projectNames = projectMemberRepository.findProjectNamesByMemberId("nicole");
        for (ProjectNameReadResponseDto projectName : projectNames) {
            System.out.println(projectName);
        }
        Assertions.assertThat(projectNames).isNotEmpty();
    }
}