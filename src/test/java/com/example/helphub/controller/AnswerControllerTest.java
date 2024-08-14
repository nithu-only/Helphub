package com.example.helphub.controller;

import com.example.helphub.dto.AnswerDTO;
import com.example.helphub.service.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerControllerTest {

    @InjectMocks
    private AnswerController answerController;

    @Mock
    private AnswerService answerService;

    private AnswerDTO answerDTO;

    @BeforeEach
    void setUp() {
        answerDTO = new AnswerDTO();
        answerDTO.setId(1L);
        answerDTO.setContent("Test Answer");
    }

    @Test
    void getAllAnswers_success() {
        when(answerService.findAll()).thenReturn(Collections.singletonList(answerDTO));

        ResponseEntity<List<AnswerDTO>> response = answerController.getAllAnswers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(answerService, times(1)).findAll();
    }

    @Test
    void getAnswerById_success() {
        when(answerService.findById(anyLong())).thenReturn(answerDTO);

        ResponseEntity<AnswerDTO> response = answerController.getAnswerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Answer", response.getBody().getContent());
        verify(answerService, times(1)).findById(anyLong());
    }

    @Test
    void createAnswer_success() {
        when(answerService.save(any(AnswerDTO.class))).thenReturn(answerDTO);

        ResponseEntity<AnswerDTO> response = answerController.createAnswer(answerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Answer", response.getBody().getContent());
        verify(answerService, times(1)).save(any(AnswerDTO.class));
    }

    @Test
    void updateAnswer_success() {
        when(answerService.update(anyLong(), any(AnswerDTO.class))).thenReturn(answerDTO);

        ResponseEntity<AnswerDTO> response = answerController.updateAnswer(1L, answerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Answer", response.getBody().getContent());
        verify(answerService, times(1)).update(anyLong(), any(AnswerDTO.class));
    }

    @Test
    void deleteAnswer_success() {
        doNothing().when(answerService).delete(anyLong());

        ResponseEntity<Void> response = answerController.deleteAnswer(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(answerService, times(1)).delete(anyLong());
    }

    //fail

    @Test
    void getAllAnswers_noContent() {
        when(answerService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<AnswerDTO>> response = answerController.getAllAnswers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());  // Check that the body is null when status is 204
        verify(answerService, times(1)).findAll();
    }


    @Test
    void getAnswerById_notFound() {
        when(answerService.findById(anyLong())).thenReturn(null);

        ResponseEntity<AnswerDTO> response = answerController.getAnswerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(answerService, times(1)).findById(anyLong());
    }

    @Test
    void createAnswer_badRequest() {
        when(answerService.save(any(AnswerDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<AnswerDTO> response = answerController.createAnswer(answerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(answerService, times(1)).save(any(AnswerDTO.class));
    }

    @Test
    void updateAnswer_notFound() {
        when(answerService.update(anyLong(), any(AnswerDTO.class))).thenReturn(null);

        ResponseEntity<AnswerDTO> response = answerController.updateAnswer(1L, answerDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(answerService, times(1)).update(anyLong(), any(AnswerDTO.class));
    }

    @Test
    void deleteAnswer_notFound() {
        doThrow(new IllegalArgumentException("Answer not found")).when(answerService).delete(anyLong());

        ResponseEntity<Void> response = answerController.deleteAnswer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(answerService, times(1)).delete(anyLong());
    }

}
