package com.example.taskapi.repository;


import com.example.taskapi.domain.CommentDto;
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
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Test
    void findAllCommentDtoByTaskId() {
        List<CommentDto> actual = commentRepository.findAllCommentDtoByTaskId(1);
        for (CommentDto commentDto : actual) {
            System.out.println(commentDto);
        }
        Assertions.assertThat(actual).isNotEmpty();

    }

}