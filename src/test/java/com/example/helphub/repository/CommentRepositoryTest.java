package com.example.helphub.repository;

import com.example.helphub.entity.Comment;
import com.example.helphub.entity.User;
import com.example.helphub.entity.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private User user;
    private Answer answer;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        answer = new Answer();
        answer.setId(1L);

        Comment comment = new Comment();
        comment.setContent("This is a comment.");
        comment.setUser(user);
        comment.setAnswer(answer);
        commentRepository.save(comment);
    }

    @Test
    void testFindByAnswerId() {
        List<Comment> comments = commentRepository.findByAnswerId(1L);
        assertNotNull(comments);
        assertEquals(1, comments.size());
    }
}
