//package com.example.helphub.service;
//
//import ch.qos.logback.core.encoder.EchoEncoder;
//import com.example.helphub.dto.QuestionTagDTO;
//import com.example.helphub.exception.ResourceNotFoundException;
//import com.example.helphub.entity.Question;
//import com.example.helphub.entity.QuestionTag;
//import com.example.helphub.entity.Tag;
//import com.example.helphub.repository.QuestionRepository;
//import com.example.helphub.repository.QuestionTagRepository;
//import com.example.helphub.repository.TagRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.BeanUtils;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class QuestionTagServiceTest {
//
//    @Mock
//    private QuestionTagRepository questionTagRepository;
//
//    @Mock
//    private QuestionRepository questionRepository;
//
//    @Mock
//    private TagRepository tagRepository;
//
//    @InjectMocks
//    private QuestionTagService questionTagService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findAll_success() {
//        QuestionTag questionTag = new QuestionTag();
//        questionTag.setId(1L);
//        when(questionTagRepository.findAll()).thenReturn(Arrays.asList(questionTag));
//
//        List<QuestionTagDTO> questionTagDTOS = questionTagService.findAll();
//
//        assertNotNull(questionTagDTOS);
//        assertEquals(1, questionTagDTOS.size());
//        assertEquals(1L, questionTagDTOS.get(0).getId());
//    }
//
//    @Test
//    void findById_success() {
//        QuestionTag questionTag = new QuestionTag();
//        questionTag.setId(1L);
//        when(questionTagRepository.findById(1L)).thenReturn(Optional.of(questionTag));
//
//
//            QuestionTagDTO questionTagDTO = questionTagService.findById(1L);
//
//        assertNotNull(questionTagDTO);
//        assertEquals(1L, questionTagDTO.getId());
//    }
//
//    @Test
//    void findById_notFound() {
//        when(questionTagRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            questionTagService.findById(1L);
//        });
//    }
//
//    @Test
//    void save_success() {
//        QuestionTag questionTag = new QuestionTag();
//        Question question = new Question();
//        question.setId(1L);
//        Tag tag = new Tag();
//        tag.setId(1L);
//
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
//        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
//        when(questionTagRepository.save(any(QuestionTag.class))).thenReturn(questionTag);
//
//        QuestionTagDTO savedQuestionTagDTO = questionTagService.save(questionTagDTO);
//
//        assertNotNull(savedQuestionTagDTO);
//        verify(questionTagRepository, times(1)).save(any(QuestionTag.class));
//    }
//
//    @Test
//    void save_questionNotFound() {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            questionTagService.save(questionTagDTO);
//        });
//    }
//
//    @Test
//    void save_tagNotFound() {
//        Question question = new Question();
//        question.setId(1L);
//
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
//        when(tagRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            questionTagService.save(questionTagDTO);
//        });
//    }
//
//    @Test
//    void update_success() {
//        QuestionTag questionTag = new QuestionTag();
//        questionTag.setId(1L);
//        Question question = new Question();
//        question.setId(1L);
//        Tag tag = new Tag();
//        tag.setId(1L);
//
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionTagRepository.findById(1L)).thenReturn(Optional.of(questionTag));
//        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
//        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
//        when(questionTagRepository.save(any(QuestionTag.class))).thenReturn(questionTag);
//
//        QuestionTagDTO updatedQuestionTagDTO = questionTagService.update(1L, questionTagDTO);
//
//        assertNotNull(updatedQuestionTagDTO);
//        assertEquals(1L, updatedQuestionTagDTO.getId());
//        verify(questionTagRepository, times(1)).save(any(QuestionTag.class));
//    }
//
//    @Test
//    void update_notFound() {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionTagRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            questionTagService.update(1L, questionTagDTO);
//        });
//    }
//
//    @Test
//    void delete_success() {
//        QuestionTag questionTag = new QuestionTag();
//        questionTag.setId(1L);
//        when(questionTagRepository.findById(1L)).thenReturn(Optional.of(questionTag));
//
//        questionTagService.delete(1L);
//
//        verify(questionTagRepository, times(1)).delete(questionTag);
//    }
//
//    @Test
//    void delete_notFound() {
//        when(questionTagRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            questionTagService.delete(1L);
//        });
//    }
//}

package com.example.helphub.service;

import com.example.helphub.dto.QuestionTagDTO;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.QuestionTag;
import com.example.helphub.entity.Tag;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.QuestionTagRepository;
import com.example.helphub.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class QuestionTagServiceTest {

    @InjectMocks
    private QuestionTagService questionTagService;

    @Mock
    private QuestionTagRepository questionTagRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TagRepository tagRepository;

    private QuestionTag questionTag;
    private QuestionTagDTO questionTagDTO;
    private Question question;
    private Tag tag;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        question = new Question();
        question.setId(1L);

        tag = new Tag();
        tag.setId(1L);

        questionTag = new QuestionTag();
        questionTag.setId(1L);
        questionTag.setQuestion(question);
        questionTag.setTag(tag);

        questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);
    }

    // Success Test Case: findAll
    @Test
    void testFindAllSuccess() {
        when(questionTagRepository.findAll()).thenReturn(Arrays.asList(questionTag));

        List<QuestionTagDTO> result = questionTagService.findAll();

        assertEquals(1, result.size());
        verify(questionTagRepository, times(1)).findAll();
    }

    // Success Test Case: findById
    @Test
    void testFindByIdSuccess() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.of(questionTag));

        QuestionTagDTO result = questionTagService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getQuestionId());
        assertEquals(1L, result.getTagId());
        verify(questionTagRepository, times(1)).findById(1L);
    }

    // Failure Test Case: findById
    @Test
    void testFindByIdFailure() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.findById(1L));
        verify(questionTagRepository, times(1)).findById(1L);
    }

    // Success Test Case: save
    @Test
    void testSaveSuccess() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        when(questionTagRepository.save(any(QuestionTag.class))).thenReturn(questionTag);

        QuestionTagDTO result = questionTagService.save(questionTagDTO);

        assertNotNull(result);
        assertEquals(1L, result.getQuestionId());
        assertEquals(1L, result.getTagId());
        verify(questionRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(questionTagRepository, times(1)).save(any(QuestionTag.class));
    }

    // Failure Test Case: save - Question Not Found
    @Test
    void testSaveFailureQuestionNotFound() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.save(questionTagDTO));
        verify(questionRepository, times(1)).findById(1L);
        verify(tagRepository, never()).findById(anyLong());
        verify(questionTagRepository, never()).save(any(QuestionTag.class));
    }

    // Failure Test Case: save - Tag Not Found
    @Test
    void testSaveFailureTagNotFound() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.save(questionTagDTO));
        verify(questionRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(questionTagRepository, never()).save(any(QuestionTag.class));
    }

    // Success Test Case: update
    @Test
    void testUpdateSuccess() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.of(questionTag));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        when(questionTagRepository.save(any(QuestionTag.class))).thenReturn(questionTag);

        QuestionTagDTO result = questionTagService.update(1L, questionTagDTO);

        assertNotNull(result);
        assertEquals(1L, result.getQuestionId());
        assertEquals(1L, result.getTagId());
        verify(questionTagRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(questionTagRepository, times(1)).save(any(QuestionTag.class));
    }

    // Failure Test Case: update - QuestionTag Not Found
    @Test
    void testUpdateFailureQuestionTagNotFound() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.update(1L, questionTagDTO));
        verify(questionTagRepository, times(1)).findById(1L);
        verify(questionRepository, never()).findById(anyLong());
        verify(tagRepository, never()).findById(anyLong());
        verify(questionTagRepository, never()).save(any(QuestionTag.class));
    }

    // Failure Test Case: update - Question Not Found
    @Test
    void testUpdateFailureQuestionNotFound() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.of(questionTag));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.update(1L, questionTagDTO));
        verify(questionTagRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(tagRepository, never()).findById(anyLong());
        verify(questionTagRepository, never()).save(any(QuestionTag.class));
    }

    // Failure Test Case: update - Tag Not Found
    @Test
    void testUpdateFailureTagNotFound() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.of(questionTag));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.update(1L, questionTagDTO));
        verify(questionTagRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(questionTagRepository, never()).save(any(QuestionTag.class));
    }

    // Success Test Case: delete
    @Test
    void testDeleteSuccess() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.of(questionTag));
        doNothing().when(questionTagRepository).delete(any(QuestionTag.class));

        questionTagService.delete(1L);

        verify(questionTagRepository, times(1)).findById(1L);
        verify(questionTagRepository, times(1)).delete(any(QuestionTag.class));
    }

    // Failure Test Case: delete - QuestionTag Not Found
    @Test
    void testDeleteFailureQuestionTagNotFound() {
        when(questionTagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionTagService.delete(1L));
        verify(questionTagRepository, times(1)).findById(1L);
        verify(questionTagRepository, never()).delete(any(QuestionTag.class));
    }
}
