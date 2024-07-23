package com.example.helphub.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class QuestionTagDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidQuestionTagDTO() {
        QuestionTagDTO dto = new QuestionTagDTO();
        dto.setId(1L);
        dto.setQuestionId(1L);
        dto.setTagId(1L);

        Set<ConstraintViolation<QuestionTagDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO should be valid");
    }

    @Test
    public void testInvalidQuestionTagDTO() {
        QuestionTagDTO dto = new QuestionTagDTO();
        dto.setId(1L); // Set valid ID
        // Not setting questionId and tagId to test validation

        Set<ConstraintViolation<QuestionTagDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "DTO should be invalid due to missing questionId and tagId");
    }
}
