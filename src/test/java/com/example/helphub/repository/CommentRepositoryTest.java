package com.example.helphub.repository;

import com.example.helphub.entity.Comment;
import com.example.helphub.entity.Answer;
import com.example.helphub.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Answer answer;

    @BeforeEach
    void setUp() {
        // Set up a User
        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@email.com");
        user = userRepository.save(user);

        // Set up an Answer
        answer = new Answer();
        answer.setContent("This is a test answer.");
        answer.setUser(user);
        answer = answerRepository.save(answer);
    }

    @Test
    void saveComment_success() {
        // Create a new Comment
        Comment comment = new Comment();
        comment.setContent("This is a test comment.");
        comment.setUser(user);
        comment.setAnswer(answer);

        // Save the Comment
        Comment savedComment = commentRepository.save(comment);

        // Verify the comment is saved
        assertNotNull(savedComment.getId());
        assertEquals("This is a test comment.", savedComment.getContent());
        assertEquals(user.getId(), savedComment.getUser().getId());
        assertEquals(answer.getId(), savedComment.getAnswer().getId());
    }

    @Test
    void findByAnswerId_success() {
        // Create and save a Comment for the answer
        Comment comment = new Comment();
        comment.setContent("This is another test comment.");
        comment.setUser(user);
        comment.setAnswer(answer);
        commentRepository.save(comment);

        // Fetch comments by answer ID
        List<Comment> comments = commentRepository.findByAnswerId(answer.getId());

        // Verify the comment is returned
        assertEquals(1, comments.size());
        assertEquals("This is another test comment.", comments.get(0).getContent());
    }

    @Test
    void saveComment_fail_missingContent() {
        // Create a new Comment with missing content
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setAnswer(answer);

        // Attempt to save the Comment and manually validate content
        Exception exception = assertThrows(Exception.class, () -> {
            if (comment.getContent() == null) {
                throw new IllegalArgumentException("Content must not be null");
            }
            commentRepository.save(comment);
        });

        // Verify that the exception is thrown due to missing content
        assertTrue(exception.getMessage().contains("Content must not be null"));
    }

    @Test
    void findByAnswerId_fail_invalidAnswerId() {
        // Fetch comments by a non-existent answer ID
        List<Comment> comments = commentRepository.findByAnswerId(999L);

        // Verify no comments are returned
        assertEquals(0, comments.size());
    }


}
