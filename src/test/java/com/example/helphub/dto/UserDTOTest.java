package com.example.helphub.dto;

import com.example.helphub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");
        userDTO.setEmail("test@example.com");

        assertEquals(1L, userDTO.getId());
        assertEquals("testuser", userDTO.getUsername());
        assertEquals("password", userDTO.getPassword());
        assertEquals("test@example.com", userDTO.getEmail());
    }

    @Test
    void testUserDTOConversion() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getEmail(), userDTO.getEmail());

        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);

        assertEquals(userDTO.getId(), newUser.getId());
        assertEquals(userDTO.getUsername(), newUser.getUsername());
        assertEquals(userDTO.getPassword(), newUser.getPassword());
        assertEquals(userDTO.getEmail(), newUser.getEmail());
    }
}
