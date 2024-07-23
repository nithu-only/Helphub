package com.example.helphub.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoteTest {

    private Vote vote;
    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        question = new Question();
        question.setId(1L);

        vote = new Vote();
        vote.setId(1L);
        vote.setUpvote(true);
        vote.setUser(user);
        vote.setQuestion(question);
    }

    @Test
    void testGetters() {
        assertEquals(1L, vote.getId());
        assertEquals(true, vote.isUpvote());
        assertEquals(1L, vote.getUser().getId());
        assertEquals(1L, vote.getQuestion().getId());
    }

    @Test
    void testSetters() {
        User newUser = new User();
        newUser.setId(2L);

        Question newQuestion = new Question();
        newQuestion.setId(2L);

        vote.setUpvote(false);
        vote.setUser(newUser);
        vote.setQuestion(newQuestion);

        assertEquals(false, vote.isUpvote());
        assertEquals(2L, vote.getUser().getId());
        assertEquals(2L, vote.getQuestion().getId());
    }
}
