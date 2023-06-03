package com.example.taskapi.repository;

import com.example.taskapi.entity.Project;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findAll() {
        List<Project> actual = projectRepository.findAll();
        for (Project project : actual) {
            System.out.println(project);
        }
        Assertions.assertThat(actual).hasSize(3);
    }

}