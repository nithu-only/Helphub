package com.example.helphub.dto;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDTOTest {

    private Validator validator;

    public QuestionDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidQuestionDTO() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Valid Title");
        questionDTO.setContent("Valid Content");
        questionDTO.setUserId(1L);

        var violations = validator.validate(questionDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidQuestionDTO() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Tit"); // Invalid title (too short)
        questionDTO.setContent(""); // Invalid content (too short)
        questionDTO.setUserId(null); // User ID is null

        var violations = validator.validate(questionDTO);
        try {
            assertFalse(violations.isEmpty());
        }catch (Exception e){assertEquals(3, violations.size()); // Expect violations for title, content, and userId
    }
    }
}
