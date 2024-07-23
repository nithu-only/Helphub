package com.example.helphub.service;

import com.example.helphub.dto.CommentDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Answer;
import com.example.helphub.entity.Comment;
import com.example.helphub.entity.User;
import com.example.helphub.repository.CommentRepository;
import com.example.helphub.repository.AnswerRepository;
import com.example.helphub.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        return convertToDTO(comment);
    }

    public List<CommentDTO> findByAnswerId(Long answerId) {
        return commentRepository.findByAnswerId(answerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO save(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + commentDTO.getUserId()));
        Answer answer = answerRepository.findById(commentDTO.getAnswerId())
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + commentDTO.getAnswerId()));

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment, "id", "createdAt", "username");
        comment.setUser(user);
        comment.setAnswer(answer);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public CommentDTO update(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));

        BeanUtils.copyProperties(commentDTO, comment, "id", "user", "answer", "createdAt");
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        commentRepository.delete(comment);
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setAnswerId(comment.getAnswer().getId());
        return commentDTO;
    }
}
