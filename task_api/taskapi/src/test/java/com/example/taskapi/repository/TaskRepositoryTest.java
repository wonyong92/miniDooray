package com.example.taskapi.repository;

import com.example.taskapi.domain.TaskDto;
import com.example.taskapi.entity.Task;
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
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Test
    void findAll() {
        List<Task> actual = taskRepository.findAll();
        for (Task task : actual) {
            System.out.println(task);
        }
        Assertions.assertThat(actual).hasSize(6);
    }

}