package com.example.helphub.repository;

import com.example.helphub.entity.Answer;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
