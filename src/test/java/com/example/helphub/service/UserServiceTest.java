package com.example.helphub.service;

import com.example.helphub.dto.UserDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.User;
import com.example.helphub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
    }

    @Test
    void testFindById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserDTO userDTO = userService.findById(1L);
        assertNotNull(userDTO);
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertNull(userDTO.getPassword());
        assertNull(userDTO.getEmail());
    }

    @Test
    void testFindById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void testSave_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");
        userDTO.setEmail("test@example.com");
        UserDTO savedUserDTO = userService.save(userDTO);
        assertNotNull(savedUserDTO);
        assertEquals(user.getUsername(), savedUserDTO.getUsername());
        assertNull(savedUserDTO.getPassword());
        assertNull(savedUserDTO.getEmail());
    }

    @Test
    void testUpdate_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updateduser");
        userDTO.setPassword("newpassword");
        userDTO.setEmail("updated@example.com");
        UserDTO updatedUserDTO = userService.update(1L, userDTO);

        assertNotNull(updatedUserDTO);
        assertEquals(userDTO.getUsername(), updatedUserDTO.getUsername());
        assertNull(updatedUserDTO.getPassword());
        assertNull(updatedUserDTO.getEmail());
    }

    @Test
    void testUpdate_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        UserDTO userDTO = new UserDTO();
        assertThrows(ResourceNotFoundException.class, () -> userService.update(1L, userDTO));
    }

    @Test
    void testDelete_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));
        assertDoesNotThrow(() -> userService.delete(1L));
    }

    @Test
    void testDelete_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.delete(1L));
    }
}

