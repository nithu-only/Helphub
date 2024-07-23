package com.example.helphub.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    private Comment comment;
    private User user;
    private Answer answer;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        answer = new Answer();
        answer.setId(1L);

        comment = new Comment();
        comment.setId(1L);
        comment.setContent("This is a comment");
        comment.setUser(user);
        comment.setAnswer(answer);
        comment.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testCommentFields() {
        assertEquals(1L, comment.getId());
        assertEquals("This is a comment", comment.getContent());
        assertEquals(user, comment.getUser());
        assertEquals(answer, comment.getAnswer());
        assertNotNull(comment.getCreatedAt());
    }

    @Test
    public void testSetCommentFields() {
        comment.setContent("Updated content");
        assertEquals("Updated content", comment.getContent());
    }

    @Test
    public void testCommentEquality() {
        Comment anotherComment = new Comment();
        anotherComment.setId(1L);
        anotherComment.setContent("This is a comment");
        anotherComment.setUser(user);
        anotherComment.setAnswer(answer);
        anotherComment.setCreatedAt(comment.getCreatedAt());

        assertEquals(comment, anotherComment);
        assertEquals(comment.hashCode(), anotherComment.hashCode());
    }

    @Test
    public void testCommentInequality() {
        Comment anotherComment = new Comment();
        anotherComment.setId(2L);

        assertNotEquals(comment, anotherComment);
    }
}
