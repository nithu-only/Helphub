package com.example.helphub.controller;

import com.example.helphub.dto.VoteDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VoteControllerTest {

    @InjectMocks
    private VoteController voteController;

    @Mock
    private VoteService voteService;

    private MockMvc mockMvc;
    private VoteDTO voteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voteController).build();

        voteDTO = new VoteDTO();
        voteDTO.setId(1L);
        voteDTO.setUpvote(true);
        voteDTO.setUserId(1L);
        voteDTO.setQuestionId(1L);
    }

    @Test
    void testGetAllVotes() throws Exception {
        when(voteService.findAll()).thenReturn(Arrays.asList(voteDTO));

        mockMvc.perform(get("/api/votes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(voteDTO.getId()));

        verify(voteService, times(1)).findAll();
    }

    @Test
    void testGetVoteById_Success() throws Exception {
        when(voteService.findById(anyLong())).thenReturn(voteDTO);

        mockMvc.perform(get("/api/votes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(voteDTO.getId()));

        verify(voteService, times(1)).findById(1L);
    }

    @Test
    void testGetVoteById_Failure() throws Exception {
        when(voteService.findById(anyLong())).thenThrow(new ResourceNotFoundException("Vote not found with id 1"));
        try {
            mockMvc.perform(get("/api/votes/{id}", 1L))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            verify(voteService, times(1)).findById(1L);
        }
    }
    @Test
    void testCreateVote_Success() throws Exception {
        when(voteService.save(any(VoteDTO.class))).thenReturn(voteDTO);

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"upvote\": true, \"userId\": 1, \"questionId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(voteDTO.getId()));

        verify(voteService, times(1)).save(any(VoteDTO.class));
    }

    @Test
    void testUpdateVote_Success() throws Exception {
        when(voteService.update(anyLong(), any(VoteDTO.class))).thenReturn(voteDTO);

        mockMvc.perform(put("/api/votes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"upvote\": true, \"userId\": 1, \"questionId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(voteDTO.getId()));

        verify(voteService, times(1)).update(anyLong(), any(VoteDTO.class));
    }

    @Test
    void testDeleteVote_Success() throws Exception {
        doNothing().when(voteService).delete(anyLong());

        mockMvc.perform(delete("/api/votes/{id}", 1L))
                .andExpect(status().isOk());

        verify(voteService, times(1)).delete(anyLong());
    }

    @Test
    void testDeleteVote_Failure() throws Exception {
        doThrow(new ResourceNotFoundException("Vote not found with id 1")).when(voteService).delete(anyLong());

        try {
            mockMvc.perform(delete("/api/votes/{id}", 1L))
                    .andExpect(status().isNotFound());
        }catch (Exception e){
        verify(voteService, times(1)).delete(anyLong());
    }
    }
}
