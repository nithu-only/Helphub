package com.example.helphub.controller;

import com.example.helphub.dto.QuestionDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuestionControllerTest {

    @InjectMocks
    private QuestionController questionController;

    @Mock
    private QuestionService questionService;

    private QuestionDTO questionDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        questionDTO = new QuestionDTO();
        questionDTO.setId(1L);
        questionDTO.setTitle("Test Title");
        questionDTO.setContent("Test Content");
        questionDTO.setUserId(1L);
    }

    @Test
    public void testGetAllQuestions() {
        List<QuestionDTO> questionList = Arrays.asList(questionDTO);

        when(questionService.findAll()).thenReturn(questionList);

        List<QuestionDTO> result = questionController.getAllQuestions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        verify(questionService, times(1)).findAll();
    }

    @Test
    public void testGetQuestionById_Success() {
        when(questionService.findById(1L)).thenReturn(questionDTO);

        QuestionDTO result = questionController.getQuestionById(1L);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(questionService, times(1)).findById(1L);
    }



    @Test
    public void testCreateQuestion_Success() {
        when(questionService.save(any(QuestionDTO.class))).thenReturn(questionDTO);

        QuestionDTO result = questionController.createQuestion(questionDTO);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(questionService, times(1)).save(any(QuestionDTO.class));
    }

    @Test
    public void testUpdateQuestion_Success() {
        when(questionService.update(eq(1L), any(QuestionDTO.class))).thenReturn(questionDTO);

        QuestionDTO result = questionController.updateQuestion(1L, questionDTO);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(questionService, times(1)).update(eq(1L), any(QuestionDTO.class));
    }

    @Test
    public void testDeleteQuestion_Success() {
        doNothing().when(questionService).delete(1L);

        questionController.deleteQuestion(1L);

        verify(questionService, times(1)).delete(1L);
    }

    //failure

    @Test
    public void testCreateQuestion_Failure() {
        when(questionService.save(any(QuestionDTO.class))).thenThrow(new IllegalArgumentException("Invalid question data"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionController.createQuestion(questionDTO);
        });

        assertEquals("Invalid question data", exception.getMessage());
        verify(questionService, times(1)).save(any(QuestionDTO.class));
    }


    @Test
    public void testGetQuestionById_NotFound() {
        when(questionService.findById(1L)).thenThrow(new ResourceNotFoundException("Question not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            questionController.getQuestionById(1L);
        });
    }

    @Test
    public void testUpdateQuestion_NotFound() {
        when(questionService.update(eq(1L), any(QuestionDTO.class))).thenThrow(new ResourceNotFoundException("Question not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            questionController.updateQuestion(1L, questionDTO);
        });
    }

    @Test
    public void testDeleteQuestion_NotFound() {
        doThrow(new ResourceNotFoundException("Question not found")).when(questionService).delete(1L);

        assertThrows(ResourceNotFoundException.class, () -> {
            questionController.deleteQuestion(1L);
        });
    }
}
