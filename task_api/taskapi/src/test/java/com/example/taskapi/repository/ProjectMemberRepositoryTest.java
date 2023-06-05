package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberDto;
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
    void findAllBy() {
        List<MemberDto> projectMembers = projectMemberRepository.findMembersByProjectId(1);
        for (MemberDto projectMember : projectMembers) {
            System.out.println(projectMember);
        }
        Assertions.assertThat(projectMembers).isNotEmpty();
    }
}