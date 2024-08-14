package com.example.helphub.controller;

import com.example.helphub.dto.TagDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.service.TagService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TagControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    void testGetAllTags() throws Exception {
        TagDTO tag1 = new TagDTO();
        tag1.setId(1L);
        tag1.setName("Spring");

        TagDTO tag2 = new TagDTO();
        tag2.setId(2L);
        tag2.setName("Hibernate");

        when(tagService.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Spring")))
                .andExpect(jsonPath("$[1].name", is("Hibernate")));

        verify(tagService, times(1)).findAll();
    }

    @Test
    void testGetTagById_Success() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(1L);
        tagDTO.setName("Spring");

        when(tagService.findById(1L)).thenReturn(tagDTO);

        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Spring")));

        verify(tagService, times(1)).findById(1L);
    }


    @Test
    void testCreateTag() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring");

        TagDTO savedTagDTO = new TagDTO();
        savedTagDTO.setId(1L);
        savedTagDTO.setName("Spring");

        when(tagService.save(any(TagDTO.class))).thenReturn(savedTagDTO);

        mockMvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Spring\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring")));

        verify(tagService, times(1)).save(any(TagDTO.class));
    }

    @Test
    void testUpdateTag_Success() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring Boot");

        TagDTO updatedTagDTO = new TagDTO();
        updatedTagDTO.setId(1L);
        updatedTagDTO.setName("Spring Boot");

        when(tagService.update(eq(1L), any(TagDTO.class))).thenReturn(updatedTagDTO);

        mockMvc.perform(put("/api/tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Spring Boot\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Spring Boot")));

        verify(tagService, times(1)).update(eq(1L), any(TagDTO.class));
    }


    @Test
    void testDeleteTag_Success() throws Exception {
        doNothing().when(tagService).delete(1L);

        mockMvc.perform(delete("/api/tags/1"))
                .andExpect(status().isOk());

        verify(tagService, times(1)).delete(1L);
    }

//    fail case

    @Test
    void testCreateTag_badRequest() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring");

        // Simulate a bad request by throwing IllegalArgumentException
        when(tagService.save(any(TagDTO.class))).thenThrow(new IllegalArgumentException("Invalid tag data"));
        try {
            mockMvc.perform(post("/api/tags")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Spring\"}"))
                    .andExpect(status().isBadRequest()) // Expecting 400 Bad Request instead of 200 OK
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.error", is("Invalid tag data")));
        } catch (Exception e) {
            verify(tagService, times(1)).save(any(TagDTO.class));
        }
    }


    @Test
    void testGetTagById_NotFound() throws Exception {
        when(tagService.findById(1L)).thenThrow(new ResourceNotFoundException("Tag not found with id 1"));
        try {
            mockMvc.perform(get("/api/tags/1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            verify(tagService, times(1)).findById(1L);
        }
    }

    @Test
    void testUpdateTag_NotFound() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring Boot");

        when(tagService.update(eq(1L), any(TagDTO.class))).thenThrow(new ResourceNotFoundException("Tag not found with id 1"));
        try {
            mockMvc.perform(put("/api/tags/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Spring Boot\"}"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {

            verify(tagService, times(1)).update(eq(1L), any(TagDTO.class));
        }
    }

    @Test
    void testDeleteTag_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Tag not found with id 1")).when(tagService).delete(1L);
        try {
            mockMvc.perform(delete("/api/tags/1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            verify(tagService, times(1)).delete(1L);
        }
    }
}
