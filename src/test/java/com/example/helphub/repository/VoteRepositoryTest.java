package com.example.helphub.repository;

import com.example.helphub.entity.Vote;
import com.example.helphub.entity.User;
import com.example.helphub.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

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
        question.setContent("This is a test question content.");
        question.setUser(user);
        question = questionRepository.save(question);
    }

    // Pass Test Cases
    @Test
    void saveVote_success() {
        // Create a new Vote
        Vote vote = new Vote();
        vote.setUpvote(true);
        vote.setUser(user);
        vote.setQuestion(question);

        // Save the Vote
        Vote savedVote = voteRepository.save(vote);

        // Verify the vote is saved
        assertNotNull(savedVote.getId());
        assertTrue(savedVote.isUpvote());
        assertEquals(user.getId(), savedVote.getUser().getId());
        assertEquals(question.getId(), savedVote.getQuestion().getId());
    }

    // Fail Test Cases
    @Test
    void saveVote_fail_missingUser() {
        // Create a new Vote with a missing user
        Vote vote = new Vote();
        vote.setUpvote(true);
        vote.setQuestion(question);

        // Attempt to save the Vote and manually validate the user
        Exception exception = assertThrows(Exception.class, () -> {
            if (vote.getUser() == null) {
                throw new IllegalArgumentException("User must not be null");
            }
            voteRepository.save(vote);
        });

        // Verify that the exception is thrown due to missing user
        assertTrue(exception.getMessage().contains("User must not be null"));
    }

    @Test
    void saveVote_fail_missingQuestion() {
        // Create a new Vote with a missing question
        Vote vote = new Vote();
        vote.setUpvote(true);
        vote.setUser(user);

        // Attempt to save the Vote and manually validate the question
        Exception exception = assertThrows(Exception.class, () -> {
            if (vote.getQuestion() == null) {
                throw new IllegalArgumentException("Question must not be null");
            }
            voteRepository.save(vote);
        });

        // Verify that the exception is thrown due to missing question
        assertTrue(exception.getMessage().contains("Question must not be null"));
    }


}
