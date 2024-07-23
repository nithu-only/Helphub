package com.example.helphub.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.helphub.dto.QuestionTagDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.QuestionTag;
import com.example.helphub.entity.Tag;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.QuestionTagRepository;
import com.example.helphub.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionTagServiceTest {

    @Mock
    private QuestionTagRepository questionTagRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private QuestionTagService questionTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_success() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setId(1L);
        when(questionTagRepository.findAll()).thenReturn(Arrays.asList(questionTag));

        List<QuestionTagDTO> questionTagDTOS = questionTagService.findAll();

        assertNotNull(questionTagDTOS);
        assertEquals(1, questionTagDTOS.size());
        assertEquals(1L, questionTagDTOS.get(0).getId());
    }

    @Test
    void findById_success() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setId(1L);
        when(questionTagRepository.findById(1L)).thenReturn(Optional.of(questionTag));


            QuestionTagDTO questionTagDTO = questionTagService.findById(1L);

        assertNotNull(questionTagDTO);
        assertEquals(1L, questionTagDTO.getId());
    }

    @Test
    void findById_notFound() {
        when(questionTagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionTagService.findById(1L);
        });
    }

    @Test
    void save_success() {
        QuestionTag questionTag = new QuestionTag();
        Question question = new Question();
        question.setId(1L);
        Tag tag = new Tag();
        tag.setId(1L);

        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(questionTagRepository.save(any(QuestionTag.class))).thenReturn(questionTag);

        QuestionTagDTO savedQuestionTagDTO = questionTagService.save(questionTagDTO);

        assertNotNull(savedQuestionTagDTO);
        verify(questionTagRepository, times(1)).save(any(QuestionTag.class));
    }

    @Test
    void save_questionNotFound() {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionTagService.save(questionTagDTO);
        });
    }

    @Test
    void save_tagNotFound() {
        Question question = new Question();
        question.setId(1L);

        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionTagService.save(questionTagDTO);
        });
    }

    @Test
    void update_success() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setId(1L);
        Question question = new Question();
        question.setId(1L);
        Tag tag = new Tag();
        tag.setId(1L);

        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionTagRepository.findById(1L)).thenReturn(Optional.of(questionTag));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(questionTagRepository.save(any(QuestionTag.class))).thenReturn(questionTag);

        QuestionTagDTO updatedQuestionTagDTO = questionTagService.update(1L, questionTagDTO);

        assertNotNull(updatedQuestionTagDTO);
        assertEquals(1L, updatedQuestionTagDTO.getId());
        verify(questionTagRepository, times(1)).save(any(QuestionTag.class));
    }

    @Test
    void update_notFound() {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionTagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionTagService.update(1L, questionTagDTO);
        });
    }

    @Test
    void delete_success() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setId(1L);
        when(questionTagRepository.findById(1L)).thenReturn(Optional.of(questionTag));

        questionTagService.delete(1L);

        verify(questionTagRepository, times(1)).delete(questionTag);
    }

    @Test
    void delete_notFound() {
        when(questionTagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            questionTagService.delete(1L);
        });
    }
}
