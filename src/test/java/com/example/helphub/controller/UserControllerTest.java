package com.example.helphub.controller;

import com.example.helphub.dto.UserDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");
        userDTO.setEmail("test@example.com");
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        when(userService.findAll()).thenReturn(Collections.singletonList(userDTO));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is("testuser")));
    }

    @Test
    void testGetUserById_Success() throws Exception {
        when(userService.findById(anyLong())).thenReturn(userDTO);
        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.findById(anyLong())).thenThrow(new ResourceNotFoundException("User not found with id 1"));
        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 1"));
    }

    @Test
    void testCreateUser_Success() throws Exception {
        when(userService.save(any(UserDTO.class))).thenReturn(userDTO);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        when(userService.update(anyLong(), any(UserDTO.class))).thenReturn(userDTO);
        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        when(userService.update(anyLong(), any(UserDTO.class))).thenThrow(new ResourceNotFoundException("User not found with id 1"));
        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 1"));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).delete(anyLong());
        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("User not found with id 1")).when(userService).delete(anyLong());
        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 1"));
    }
}
