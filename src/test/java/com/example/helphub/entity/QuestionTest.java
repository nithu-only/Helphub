package com.example.helphub.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void testQuestionEntity() {
        Question question = new Question();
        question.setId(1L);
        question.setTitle("Test Title");
        question.setContent("Test Content");

        assertEquals(1L, question.getId());
        assertEquals("Test Title", question.getTitle());
        assertEquals("Test Content", question.getContent());
    }
}
