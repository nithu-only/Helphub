package com.example.helphub.controller;

import com.example.helphub.dto.CommentDTO;
import com.example.helphub.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    private CommentDTO commentDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentDTO = new CommentDTO();
        commentDTO.setContent("This is a comment");
        commentDTO.setUserId(1L);
        commentDTO.setAnswerId(1L);
    }

    @Test
    public void testCreateCommentSuccess() {
        when(commentService.save(any(CommentDTO.class))).thenReturn(commentDTO);

        ResponseEntity<CommentDTO> response = commentController.createComment(commentDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(commentDTO, response.getBody());
    }

    @Test
    public void testGetAllCommentsSuccess() {
        when(commentService.findAll()).thenReturn(Arrays.asList(commentDTO));

        List<CommentDTO> comments = commentController.getAllComments();

        assertEquals(1, comments.size());
        assertEquals(commentDTO, comments.get(0));
    }

    @Test
    public void testGetCommentByIdSuccess() {
        when(commentService.findById(1L)).thenReturn(commentDTO);

        CommentDTO foundComment = commentController.getCommentById(1L);

        assertEquals(commentDTO, foundComment);
    }

    @Test
    public void testUpdateCommentSuccess() {
        when(commentService.update(eq(1L), any(CommentDTO.class))).thenReturn(commentDTO);

        CommentDTO updatedComment = commentController.updateComment(1L, commentDTO);

        assertEquals(commentDTO, updatedComment);
    }

    @Test
    public void testDeleteCommentSuccess() {
        doNothing().when(commentService).delete(1L);

        assertDoesNotThrow(() -> {
            commentController.deleteComment(1L);
        });

        verify(commentService, times(1)).delete(1L);
    }

    //failure

    @Test
    public void testCreateCommentFailure() {
        when(commentService.save(any(CommentDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid comment data"));

        // Act
        ResponseEntity<CommentDTO> response = commentController.createComment(commentDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllCommentsFailure() {
        when(commentService.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<CommentDTO> comments = commentController.getAllComments();

        // Assert
        assertTrue(comments.isEmpty());
    }

    @Test
    public void testGetCommentByIdFailure() {
        when(commentService.findById(1L)).thenReturn(null);

        // Act
        CommentDTO foundComment = commentController.getCommentById(1L);

        // Assert
        assertNull(foundComment);
    }

    @Test
    public void testUpdateCommentFailure() {
        when(commentService.update(eq(1L), any(CommentDTO.class))).thenReturn(null);

        // Act
        CommentDTO updatedComment = commentController.updateComment(1L, commentDTO);

        // Assert
        assertNull(updatedComment);
    }

    @Test
    public void testDeleteCommentFailure() {
        doThrow(new IllegalArgumentException("Comment not found")).when(commentService).delete(1L);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            commentController.deleteComment(1L);
        });
    }



}
