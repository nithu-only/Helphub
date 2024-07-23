package com.example.helphub.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerDTOTest {

    @Test
    void testAnswerDTOProperties() {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setContent("Test DTO Answer");
        answerDTO.setId(1L);

        assertEquals("Test DTO Answer", answerDTO.getContent());
        assertEquals(1L, answerDTO.getId());
    }
}
