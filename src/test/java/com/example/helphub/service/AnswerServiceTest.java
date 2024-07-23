package com.example.helphub.service;

import com.example.helphub.dto.AnswerDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Answer;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.repository.AnswerRepository;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    private Answer answer;
    private AnswerDTO answerDTO;
    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        question = new Question();
        question.setId(1L);
        question.setTitle("Test Question");

        answer = new Answer();
        answer.setId(1L);
        answer.setContent("This is a test answer.");
        answer.setCreatedAt(LocalDateTime.now());
        answer.setUser(user);
        answer.setQuestion(question);

        answerDTO = new AnswerDTO();
        BeanUtils.copyProperties(answer, answerDTO);
        answerDTO.setUserId(user.getId());
        answerDTO.setQuestionId(question.getId());
    }

    @Test
    void findAll_success() {
        when(answerRepository.findAll()).thenReturn(Arrays.asList(answer));
        assertEquals(1, answerService.findAll().size());
        verify(answerRepository, times(1)).findAll();
    }

    @Test
    void findById_success() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(answer));
        AnswerDTO foundAnswer = answerService.findById(1L);
        assertEquals("This is a test answer.", foundAnswer.getContent());
        verify(answerRepository, times(1)).findById(anyLong());
    }

    @Test
    void findById_notFound() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> answerService.findById(1L));
        verify(answerRepository, times(1)).findById(anyLong());
    }

    @Test
    void save_success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        AnswerDTO savedAnswer = answerService.save(answerDTO);
        assertEquals("This is a test answer.", savedAnswer.getContent());
        verify(userRepository, times(1)).findById(anyLong());
        verify(questionRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(1)).save(any(Answer.class));
    }

    @Test
    void save_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> answerService.save(answerDTO));
        verify(userRepository, times(1)).findById(anyLong());
        verify(questionRepository, times(0)).findById(anyLong());
        verify(answerRepository, times(0)).save(any(Answer.class));
    }

    @Test
    void save_questionNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> answerService.save(answerDTO));
        verify(userRepository, times(1)).findById(anyLong());
        verify(questionRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(0)).save(any(Answer.class));
    }

    @Test
    void update_success() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(answer));
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        AnswerDTO updatedAnswer = answerService.update(1L, answerDTO);
        assertEquals("This is a test answer.", updatedAnswer.getContent());
        verify(answerRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(1)).save(any(Answer.class));
    }

    @Test
    void update_notFound() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> answerService.update(1L, answerDTO));
        verify(answerRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(0)).save(any(Answer.class));
    }

    @Test
    void delete_success() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(answer));
        doNothing().when(answerRepository).delete(any(Answer.class));

        answerService.delete(1L);
        verify(answerRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(1)).delete(any(Answer.class));
    }

    @Test
    void delete_notFound() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> answerService.delete(1L));
        verify(answerRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(0)).delete(any(Answer.class));
    }
}
