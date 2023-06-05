package com.example.taskapi.repository;


import com.example.taskapi.domain.MilestoneDto;
import com.example.taskapi.entity.Milestone;
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
class MilestoneRepositoryTest {

    @Autowired
    MilestoneRepository milestoneRepository;

    @Test
    void findAll() {
        List<Milestone> actual =
                milestoneRepository.findAll();
        for (Milestone milestone : actual) {
            System.out.println(milestone);
        }
        Assertions.assertThat(actual).isNotEmpty();
    }
    @Test
    void findAllProjectDtoByProjectId() {
        List<MilestoneDto> actual = milestoneRepository.findAllMilestoneDtoByProjectId(1);
        for (MilestoneDto milestoneDto : actual) {
            System.out.println(milestoneDto);
        }
        Assertions.assertThat(actual).isNotEmpty();
    }
}