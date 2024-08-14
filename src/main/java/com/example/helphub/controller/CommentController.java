package com.example.helphub.controller;

import com.example.helphub.dto.CommentDTO;
import com.example.helphub.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @GetMapping("/answer/{answerId}")
    public List<CommentDTO> getCommentsByAnswerId(@PathVariable Long answerId) {
        return commentService.findByAnswerId(answerId);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        try {
            CommentDTO savedComment = commentService.save(commentDTO);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public CommentDTO updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO) {
        return commentService.update(id, commentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }
}
