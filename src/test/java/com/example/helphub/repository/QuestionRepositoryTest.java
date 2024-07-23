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
        userRepository.save(user);
    }

    @Test
    public void testSaveAndFindById() {
        Question question = new Question();
        question.setTitle("Valid Title");
        question.setContent("This content is valid because it is at least 20 characters long."); // Ensure this is valid
        question.setUser(user);

        questionRepository.save(question);

        Question foundQuestion = questionRepository.findById(question.getId()).orElse(null);
        assertNotNull(foundQuestion);
        assertEquals("Valid Title", foundQuestion.getTitle());
    }



    @Test
    public void testFindByUserId() {
        Question question1 = new Question();
        question1.setTitle("Test Title 1");
        question1.setContent("This content is valid as it is longer than 20 characters."); // Ensure valid content
        question1.setUser(user);
        questionRepository.save(question1);

        Question question2 = new Question();
        question2.setTitle("Test Title 2");
        question2.setContent("This is another valid content example that is sufficiently long."); // Ensure valid content
        question2.setUser(user);
        questionRepository.save(question2);

        List<Question> questions = questionRepository.findByUserId(user.getId());
        assertEquals(2, questions.size());
    }

}
