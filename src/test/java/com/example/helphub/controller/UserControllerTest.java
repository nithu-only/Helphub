package com.example.helphub.controller;

import com.example.helphub.dto.UserDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is("testuser")));

        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        when(userService.findById(anyLong())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")));

        verify(userService, times(1)).findById(anyLong());
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.findById(anyLong())).thenThrow(new ResourceNotFoundException("User not found with id 1"));
        try {
            mockMvc.perform(get("/api/users/{id}", 1L))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("User not found with id 1"));
        } catch (Exception e) {

            verify(userService, times(1)).findById(anyLong());
        }
    }

    @Test
    void testGetUserByUsername_Success() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/username/{username}", "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")));

        verify(userService, times(1)).findByUsername(anyString());
    }

    @Test
    void testGetUserByEmail_Success() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/email/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(userService, times(1)).findByEmail(anyString());
    }

    @Test
    public void testCreateUserSuccess() {
        when(userService.save(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }


    @Test
    public void testUpdateUserSuccess() {
        when(userService.update(eq(1L), any(UserDTO.class))).thenReturn(userDTO);

        UserDTO updatedUser = userController.updateUser(1L, userDTO);

        assertEquals(userDTO, updatedUser);
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mockMvc.perform(delete("/api/{id}", 1L))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(anyLong());
    }


//    fail case

    @Test
    public void testCreateUserFailure() {
        when(userService.save(any(UserDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid user data"));

        // Act
        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllUsersFailure() {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<UserDTO> users = userController.getAllUsers();

        // Assert
        assertTrue(users.isEmpty());
    }

    @Test
    public void testGetUserByIdFailure() {
        when(userService.findById(1L)).thenReturn(null);

        // Act
        UserDTO foundUser = userController.getUserById(1L);

        // Assert
        assertNull(foundUser);
    }

    @Test
    public void testUpdateUserFailure() {
        when(userService.update(eq(1L), any(UserDTO.class))).thenReturn(null);

        // Act
        UserDTO updatedUser = userController.updateUser(1L, userDTO);

        // Assert
        assertNull(updatedUser);
    }

    @Test
    public void testDeleteUserFailure() {
        doThrow(new IllegalArgumentException("User not found")).when(userService).delete(1L);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userController.deleteUser(1L);
        });
    }

}
