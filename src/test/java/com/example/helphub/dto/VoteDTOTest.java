package com.example.helphub.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoteDTOTest {

    private VoteDTO voteDTO;

    @BeforeEach
    void setUp() {
        voteDTO = new VoteDTO();
        voteDTO.setId(1L);
        voteDTO.setUpvote(true);
        voteDTO.setUserId(1L);
        voteDTO.setQuestionId(1L);
    }

    @Test
    void testGetters() {
        assertEquals(1L, voteDTO.getId());
        assertEquals(true, voteDTO.isUpvote());
        assertEquals(1L, voteDTO.getUserId());
        assertEquals(1L, voteDTO.getQuestionId());
    }

    @Test
    void testSetters() {
        voteDTO.setUpvote(false);
        voteDTO.setUserId(2L);
        voteDTO.setQuestionId(2L);

        assertEquals(false, voteDTO.isUpvote());
        assertEquals(2L, voteDTO.getUserId());
        assertEquals(2L, voteDTO.getQuestionId());
    }
}
