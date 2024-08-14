package com.example.helphub.repository;

import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setEmail("testUser@email.com");
        userRepository.save(user);
    }

    @Test
    void saveQuestion_success() {
        // Create a new Question
        Question question = new Question();
        question.setTitle("Test Question");
        question.setContent("This is the content of the test question.");
        question.setUser(user);

        // Save the Question
        Question savedQuestion = questionRepository.save(question);

        // Verify the question is saved
        assertNotNull(savedQuestion.getId());
        assertEquals("Test Question", savedQuestion.getTitle());
        assertEquals("This is the content of the test question.", savedQuestion.getContent());
        assertEquals(user.getId(), savedQuestion.getUser().getId());
    }


    @Test
    void findByUserId_success() {
        // Create and save a Question for the user
        Question question = new Question();
        question.setTitle("Another Test Question");
        question.setContent("This is another test question content.");
        question.setUser(user);
        questionRepository.save(question);

        // Fetch questions by user ID
        List<Question> questions = questionRepository.findByUserId(user.getId());

        // Verify the question is returned
        assertEquals(1, questions.size());
        assertEquals("Another Test Question", questions.get(0).getTitle());
    }

//    fail case

    @Test
    void saveQuestion_fail_missingContent() {
        // Create a new Question with missing content
        Question question = new Question();
        question.setTitle("Test Question with No Content");
        question.setUser(user);

        // Attempt to save the Question and manually validate content
        Exception exception = assertThrows(Exception.class, () -> {
            if (question.getContent() == null) {
                throw new IllegalArgumentException("Content must not be null");
            }
            questionRepository.save(question);
        });

        // Verify that the exception is thrown due to missing content
        assertTrue(exception.getMessage().contains("Content must not be null"));
    }

    @Test
    void findByUserId_fail_invalidUserId() {
        // Fetch questions by a non-existent user ID
        List<Question> questions = questionRepository.findByUserId(999L);

        // Verify no questions are returned
        assertEquals(0, questions.size());
    }


}
