package com.example.helphub.service;

import com.example.helphub.dto.CommentDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Comment;
import com.example.helphub.entity.User;
import com.example.helphub.entity.Answer;
import com.example.helphub.repository.CommentRepository;
import com.example.helphub.repository.UserRepository;
import com.example.helphub.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnswerRepository answerRepository;

    private Comment comment;
    private User user;
    private Answer answer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
    public void testSaveCommentSuccess() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("This is a comment");
        commentDTO.setUserId(1L);
        commentDTO.setAnswerId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO savedComment = commentService.save(commentDTO);

        assertNotNull(savedComment);
        assertEquals("This is a comment", savedComment.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void testSaveCommentUserNotFound() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("This is a comment");
        commentDTO.setUserId(1L);
        commentDTO.setAnswerId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.save(commentDTO);
        });

        assertEquals("User not found with id 1", thrown.getMessage());
    }

    @Test
    public void testFindByIdSuccess() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentDTO foundComment = commentService.findById(1L);

        assertNotNull(foundComment);
        assertEquals("This is a comment", foundComment.getContent());
    }

    @Test
    public void testFindByIdNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.findById(1L);
        });

        assertEquals("Comment not found with id 1", thrown.getMessage());
    }

    @Test
    public void testUpdateCommentSuccess() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Updated comment");

        CommentDTO updatedComment = commentService.update(1L, commentDTO);

        assertEquals("Updated comment", updatedComment.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void testUpdateCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Updated comment");

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.update(1L, commentDTO);
        });

        assertEquals("Comment not found with id 1", thrown.getMessage());
    }

    @Test
    public void testDeleteCommentSuccess() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        assertDoesNotThrow(() -> {
            commentService.delete(1L);
        });

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    public void testDeleteCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.delete(1L);
        });

        assertEquals("Comment not found with id 1", thrown.getMessage());
    }
}
