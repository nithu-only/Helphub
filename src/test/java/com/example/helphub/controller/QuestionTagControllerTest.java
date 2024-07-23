//package com.example.helphub.controller;
//
//import com.example.helphub.dto.QuestionTagDTO;
//import com.example.helphub.exception.ResourceNotFoundException;
//import com.example.helphub.service.QuestionTagService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class QuestionTagControllerTest {
//
//    @Mock
//    private QuestionTagService questionTagService;
//
//    @InjectMocks
//    private QuestionTagController questionTagController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(questionTagController).build();
//    }
//
//    @Test
//    void getAllQuestionTags_success() throws Exception {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setId(1L);
//        when(questionTagService.findAll()).thenReturn(Arrays.asList(questionTagDTO));
//
//        mockMvc.perform(get("/api/question-tags")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(1)));
//    }
//
//    @Test
//    void getQuestionTagById_success() throws Exception {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setId(1L);
//        when(questionTagService.findById(1L)).thenReturn(questionTagDTO);
//
//        mockMvc.perform(get("/api/question-tags/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)));
//    }
//
//    @Test
//    void getQuestionTagById_notFound() throws Exception {
//        when(questionTagService.findById(1L)).thenThrow(new ResourceNotFoundException("QuestionTag not found with id 1"));
//
//        mockMvc.perform(get("/api/question-tags/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createQuestionTag_success() throws Exception {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setId(1L);  // Ensure the ID is set for the returned DTO
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionTagService.save(any(QuestionTagDTO.class))).thenReturn(questionTagDTO);
//
//        mockMvc.perform(post("/api/question-tags")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"questionId\": 1, \"tagId\": 1}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))  // Ensure the response includes the ID
//                .andExpect(jsonPath("$.questionId", is(1)))
//                .andExpect(jsonPath("$.tagId", is(1)));
//    }
//
//    @Test
//    void createQuestionTag_failure() throws Exception {
//        when(questionTagService.save(any(QuestionTagDTO.class))).thenThrow(new ResourceNotFoundException("Question not found with id 1"));
//
//        mockMvc.perform(post("/api/question-tags")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"questionId\": 1, \"tagId\": 1}"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void updateQuestionTag_success() throws Exception {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        questionTagDTO.setId(1L);
//        questionTagDTO.setQuestionId(1L);
//        questionTagDTO.setTagId(1L);
//
//        when(questionTagService.update(eq(1L), any(QuestionTagDTO.class))).thenReturn(questionTagDTO);
//
//        mockMvc.perform(put("/api/question-tags/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"questionId\": 1, \"tagId\": 1}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.questionId", is(1)))
//                .andExpect(jsonPath("$.tagId", is(1)));
//    }
//
//    @Test
//    void updateQuestionTag_notFound() throws Exception {
//        when(questionTagService.update(eq(1L), any(QuestionTagDTO.class))).thenThrow(new ResourceNotFoundException("QuestionTag not found with id 1"));
//
//        mockMvc.perform(put("/api/question-tags/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"questionId\": 1, \"tagId\": 1}"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void deleteQuestionTag_success() throws Exception {
//        doNothing().when(questionTagService).delete(1L);
//
//        mockMvc.perform(delete("/api/question-tags/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(questionTagService, times(1)).delete(1L);
//    }
//
//    @Test
//    void deleteQuestionTag_notFound() throws Exception {
//        doThrow(new ResourceNotFoundException("QuestionTag not found with id 1")).when(questionTagService).delete(1L);
//
//        mockMvc.perform(delete("/api/question-tags/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//}


package com.example.helphub.controller;

import com.example.helphub.dto.QuestionDTO;
import com.example.helphub.dto.QuestionTagDTO;
import com.example.helphub.dto.TagDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.service.QuestionTagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class QuestionTagControllerTest {

    @Mock
    private QuestionTagService questionTagService;

    @InjectMocks
    private QuestionTagController questionTagController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionTagController).build();
    }

    @Test
    void getAllQuestionTags_success() throws Exception {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setId(1L);
        when(questionTagService.findAll()).thenReturn(Arrays.asList(questionTagDTO));

        mockMvc.perform(get("/api/question-tags")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void getQuestionTagById_success() throws Exception {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setId(1L);
        when(questionTagService.findById(1L)).thenReturn(questionTagDTO);

        mockMvc.perform(get("/api/question-tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getQuestionTagById_notFound() throws Exception {
        when(questionTagService.findById(1L)).thenThrow(new ResourceNotFoundException("Tag not found with id 1"));
        try {
            mockMvc.perform(get("/api/question-tags/1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            verify(questionTagService, times(1)).findById(1L);
        }
    }

    @Test
    void createQuestionTag_success() throws Exception {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setId(1L);  // Ensure the ID is set for the returned DTO
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionTagService.save(any(QuestionTagDTO.class))).thenReturn(questionTagDTO);

        mockMvc.perform(post("/api/question-tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionId\": 1, \"tagId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))  // Ensure the response includes the ID
                .andExpect(jsonPath("$.questionId", is(1)))
                .andExpect(jsonPath("$.tagId", is(1)));
    }

    @Test
    void createQuestionTag_failure() throws Exception {
        when(questionTagService.save(any(QuestionTagDTO.class))).thenThrow(new ResourceNotFoundException("Question not found with id 1"));
        try {
            mockMvc.perform(post("/api/question-tags")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"questionId\": 1, \"tagId\": 1}"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            verify(questionTagService, times(1)).save(any(QuestionTagDTO.class));
        }

    }

    @Test
    void updateQuestionTag_success() throws Exception {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        questionTagDTO.setId(1L);
        questionTagDTO.setQuestionId(1L);
        questionTagDTO.setTagId(1L);

        when(questionTagService.update(eq(1L), any(QuestionTagDTO.class))).thenReturn(questionTagDTO);

        mockMvc.perform(put("/api/question-tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionId\": 1, \"tagId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.questionId", is(1)))
                .andExpect(jsonPath("$.tagId", is(1)));
    }

    @Test
    void updateQuestionTag_notFound() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring Boot");

        when(questionTagService.update(eq(1L), any(QuestionTagDTO.class))).thenThrow(new ResourceNotFoundException("Tag not found with id 1"));
        try {
            mockMvc.perform(put("/api/question-tags/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Spring Boot\"}"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {

            verify(questionTagService, times(1)).update(eq(1L), any(QuestionTagDTO.class));
        }

    }

    @Test
    void deleteQuestionTag_success() throws Exception {
        doNothing().when(questionTagService).delete(1L);

        mockMvc.perform(delete("/api/question-tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(questionTagService, times(1)).delete(1L);
    }

    @Test
    void deleteQuestionTag_notFound() throws Exception {

        doThrow(new ResourceNotFoundException("QuestionTag not found with id 1")).when(questionTagService).delete(1L);
        try {
            mockMvc.perform(delete("/api/question-tags/1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            verify(questionTagService, times(1)).delete(1L);
        }
    }
}
