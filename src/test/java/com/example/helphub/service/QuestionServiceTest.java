package com.example.helphub.service;

import com.example.helphub.dto.QuestionDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Question question;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        question = new Question();
        question.setId(1L);
        question.setTitle("Test Title");
        question.setContent("Test Content");
        question.setCreatedAt(LocalDateTime.now());
        question.setUser(user);
    }

    @Test
    public void testSaveQuestion_Success() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Test Title");
        questionDTO.setContent("Test Content");
        questionDTO.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        QuestionDTO savedQuestion = questionService.save(questionDTO);

        assertNotNull(savedQuestion);
        assertEquals("Test Title", savedQuestion.getTitle());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    public void testSaveQuestion_UserNotFound() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Test Title");
        questionDTO.setContent("Test Content");
        questionDTO.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.save(questionDTO);
        });
    }

    @Test
    public void testUpdateQuestion_Success() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Updated Title");
        questionDTO.setContent("Updated Content");
        questionDTO.setUserId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        QuestionDTO updatedQuestion = questionService.update(1L, questionDTO);

        assertNotNull(updatedQuestion);
        assertEquals("Updated Title", updatedQuestion.getTitle());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    public void testUpdateQuestion_NotFound() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Updated Title");
        questionDTO.setContent("Updated Content");

        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.update(1L, questionDTO);
        });
    }

    @Test
    public void testDeleteQuestion_Success() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        questionService.delete(1L);

        verify(questionRepository, times(1)).delete(question);
    }

    @Test
    public void testDeleteQuestion_NotFound() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.delete(1L);
        });
    }

    @Test
    public void testFindAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question));

        List<QuestionDTO> questions = questionService.findAll();

        assertEquals(1, questions.size());
        assertEquals("Test Title", questions.get(0).getTitle());
    }

    @Test
    public void testFindById_Success() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        QuestionDTO foundQuestion = questionService.findById(1L);

        assertNotNull(foundQuestion);
        assertEquals("Test Title", foundQuestion.getTitle());
    }

    @Test
    public void testFindById_NotFound() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.findById(1L);
        });
    }

    @Test
    public void testFindByUserId_Success() {
        when(questionRepository.findByUserId(1L)).thenReturn(Arrays.asList(question));

        List<QuestionDTO> questions = questionService.findByUserId(1L);

        assertEquals(1, questions.size());
        assertEquals("Test Title", questions.get(0).getTitle());
    }
}
