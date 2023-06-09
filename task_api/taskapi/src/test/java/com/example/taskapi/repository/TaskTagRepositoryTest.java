package com.example.taskapi.repository;

import com.example.taskapi.domain.TagReadResponseDto;
import com.example.taskapi.entity.Tag;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.TaskTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TaskTagRepositoryTest {

    @Autowired
    private TaskTagRepository taskTagRepository;


    @Test
    void test() {
        List<TaskTag> allTagByTaskTaskId = taskTagRepository.findAllTagByTask_TaskId(1);
        for (TaskTag taskTag : allTagByTaskTaskId) {
            System.out.println(taskTag);
            Tag tag = taskTag.getTag();
            System.out.println(tag);
        }
    }

}