package com.example.taskapi.repository;

import com.example.taskapi.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;
    @Test
    void findAllByProject_ProjectId() {
    }

    @Test
    void findTagByProject_projectIdAndTagId() {
        Optional<Tag> tag = tagRepository.findTagByProject_projectIdAndTagId(1, 2);
        System.out.println(tag.get());
    }
}