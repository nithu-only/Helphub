package com.example.helphub.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerTest {

    @Test
    void testAnswerProperties() {
        Answer answer = new Answer();
        answer.setContent("Test Answer");
        answer.setId(1L);

        assertEquals("Test Answer", answer.getContent());
        assertEquals(1L, answer.getId());
    }
}
