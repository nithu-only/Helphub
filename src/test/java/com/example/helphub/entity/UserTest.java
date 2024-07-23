package com.example.helphub.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testUserEntityEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser");
        user1.setPassword("password");
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("testuser");
        user2.setPassword("password");
        user2.setEmail("test@example.com");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
