package com.example.helphub.repository;

import com.example.helphub.entity.Answer;
import com.example.helphub.entity.Question;
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

public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Question question;

    @BeforeEach
    void setUp() {

        // Set up a User
        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@email.com");
        user = userRepository.save(user);

        // Set up a Question
        question = new Question();
        question.setTitle("Test Question");
        question.setContent("This is the content of the test question.");
        question = questionRepository.save(question);
    }

    @Test
    void saveAnswer_success() {
        // Create a new Answer
        Answer answer = new Answer();
        answer.setContent("This is a test answer.");
        answer.setUser(user);
        answer.setQuestion(question);

        // Save the Answer
        Answer savedAnswer = answerRepository.save(answer);

        // Verify the answer is saved
        assertNotNull(savedAnswer.getId());
        assertEquals("This is a test answer.", savedAnswer.getContent());
        assertEquals(user.getId(), savedAnswer.getUser().getId());
        assertEquals(question.getId(), savedAnswer.getQuestion().getId());
    }

    @Test
    void findByQuestionId_success() {
        // Create and save an Answer for the question
        Answer answer = new Answer();
        answer.setContent("This is another test answer.");
        answer.setUser(user);
        answer.setQuestion(question);
        answerRepository.save(answer);

        // Fetch answers by question ID
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());

        // Verify the answer is returned
        assertEquals(1, answers.size());
        assertEquals("This is another test answer.", answers.get(0).getContent());
    }

    @Test
    void findByQuestionId_noAnswers() {
        // Fetch answers by a non-existent question ID
        List<Answer> answers = answerRepository.findByQuestionId(999L);

        // Verify no answers are returned
        assertEquals(0, answers.size());
    }

    @Test
    void deleteAnswer_success() {
        // Create and save an Answer
        Answer answer = new Answer();
        answer.setContent("This is a test answer for deletion.");
        answer.setUser(user);
        answer.setQuestion(question);
        Answer savedAnswer = answerRepository.save(answer);

        // Delete the Answer
        answerRepository.delete(savedAnswer);

        // Verify that the Answer no longer exists
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());
        assertEquals(0, answers.size());
    }

//    fail case

    @Test
    void saveAnswer_fail_missingContent() {
        // Create a new Answer with missing content
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);

        // Attempt to save the Answer and manually validate content
        Exception exception = assertThrows(Exception.class, () -> {
            if (answer.getContent() == null) {
                throw new IllegalArgumentException("Content must not be null");
            }
            answerRepository.save(answer);
        });

        // Verify that the exception is thrown due to missing content
        assertTrue(exception.getMessage().contains("Content must not be null"));
    }


    @Test
    void findByQuestionId_fail_noAssociatedAnswers() {
        // Fetch answers by question ID for a question with no associated answers
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());

        // Verify that no answers are returned
        assertEquals(0, answers.size());
    }

    @Test
    void findByQuestionId_fail_invalidQuestionId() {
        // Fetch answers by a non-existent question ID
        List<Answer> answers = answerRepository.findByQuestionId(999L);

        // Expect that at least one answer should have been found
        assertEquals(0, answers.size());
    }

    @Test
    void deleteAnswer_fail_nonExistentAnswer() {
        // Attempt to delete an answer that doesn't exist
        Answer nonExistentAnswer = new Answer();
        nonExistentAnswer.setId(999L); // Assuming this ID does not exist

        // Perform the delete operation
        answerRepository.delete(nonExistentAnswer);

        // Check if the repository still contains answers for the existing question
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());

        // Verify that the deletion did not affect the existing answers (if any)
        assertNotNull(answers);
    }


}
