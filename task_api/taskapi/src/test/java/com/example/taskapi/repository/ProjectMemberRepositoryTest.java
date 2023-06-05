package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberDto;
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
    void findAllProjectMemberDtoByProjectId() {
        List<MemberDto> actual = projectMemberRepository.findAllProjectMemberDtoByProjectId(1);
        for (MemberDto memberDto : actual) {
            System.out.println(memberDto);
        }
        Assertions.assertThat(actual).isNotEmpty();
    }
}