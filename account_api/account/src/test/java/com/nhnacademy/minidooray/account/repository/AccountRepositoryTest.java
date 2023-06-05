//package com.nhnacademy.minidooray.account.repository;
//
//import dev.oxingaxin.springbootboard.entity.Board;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//@Slf4j
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//class AccountRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testBoard() {
//        Board newBoard = new Board();
//
//        newBoard.setTitle("Test Board");
//        newBoard.setContent("Test Content");
//        newBoard.setWriter("Tester");
//
//        entityManager.persist(newBoard);
//        entityManager.flush();
//
//        entityManager.clear();
//
//        Long newBoardId = newBoard.getId();
//        log.info("newBoard Id={}", newBoardId);
//
//        Board persistedBoard = entityManager.find(Board.class, newBoardId);
//
//        assertThat(persistedBoard).isNotNull();
//        assertThat(persistedBoard.getTitle()).isEqualTo("Test Board");
//        assertThat(persistedBoard.getContent()).isEqualTo("Test Content");
//        assertThat(persistedBoard.getWriter()).isEqualTo("Tester");
//    }
//}
