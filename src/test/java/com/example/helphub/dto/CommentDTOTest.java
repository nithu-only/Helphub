package com.example.helphub.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentDTOTest {

    private CommentDTO commentDTO;

    @BeforeEach
    public void setUp() {
        commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setContent("This is a comment");
        commentDTO.setUserId(1L);
        commentDTO.setAnswerId(1L);
    }

    @Test
    public void testCommentDTOFields() {
        assertEquals(1L, commentDTO.getId());
        assertEquals("This is a comment", commentDTO.getContent());
        assertEquals(1L, commentDTO.getUserId());
        assertEquals(1L, commentDTO.getAnswerId());
    }

    @Test
    public void testSetCommentDTOFields() {
        commentDTO.setContent("Updated content");
        assertEquals("Updated content", commentDTO.getContent());
    }

    @Test
    public void testCommentDTOEquality() {
        CommentDTO anotherCommentDTO = new CommentDTO();
        anotherCommentDTO.setId(1L);
        anotherCommentDTO.setContent("This is a comment");
        anotherCommentDTO.setUserId(1L);
        anotherCommentDTO.setAnswerId(1L);

        assertEquals(commentDTO, anotherCommentDTO);
        assertEquals(commentDTO.hashCode(), anotherCommentDTO.hashCode());
    }

    @Test
    public void testCommentDTOInequality() {
        CommentDTO anotherCommentDTO = new CommentDTO();
        anotherCommentDTO.setId(2L);

        assertNotEquals(commentDTO, anotherCommentDTO);
    }
}
