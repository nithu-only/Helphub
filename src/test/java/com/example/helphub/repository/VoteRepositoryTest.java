package com.example.helphub.repository;

import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.entity.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private User user;
    private Question question;
    private Vote vote;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user); // Save to generate ID

        question = new Question();
        question.setTitle("Test Question");
        question.setContent("This is the content of the test question."); // Set content
        question = questionRepository.save(question); // Save to generate ID

        vote = new Vote();
        vote.setUpvote(true);
        vote.setUser(user);
        vote.setQuestion(question);
        voteRepository.save(vote); // Save the vote
    }


    @Test
    void testFindAll() {
        List<Vote> votes = voteRepository.findAll();
        assertEquals(1, votes.size());
        assertEquals(vote.getId(), votes.get(0).getId());
    }

    @Test
    void testFindById() {
        Vote foundVote = voteRepository.findById(vote.getId()).orElse(null);
        assertEquals(vote.getId(), foundVote.getId());
    }

    @Test
    void testSave() {
        Vote newVote = new Vote();
        newVote.setUpvote(false);
        newVote.setUser(user);
        newVote.setQuestion(question);
        voteRepository.save(newVote);

        Vote foundVote = voteRepository.findById(newVote.getId()).orElse(null);
        assertEquals(newVote.getId(), foundVote.getId());
    }

    @Test
    void testDelete() {
        voteRepository.delete(vote);
        List<Vote> votes = voteRepository.findAll();
        assertTrue(votes.isEmpty());
    }
}
