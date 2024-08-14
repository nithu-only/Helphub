package com.example.helphub.service;

import com.example.helphub.dto.QuestionDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO findById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));
        return convertToDTO(question);
    }

    public List<QuestionDTO> findByUserId(Long userId) {
        return questionRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO save(QuestionDTO questionDTO) {
        User user = userRepository.findById(questionDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + questionDTO.getUserId()));
        System.out.println("asldflakjsdflkajsdlfkjaslkdfjalsfkdjldksjldfjl"+user);
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO, question);
        question.setUser(user);
        question.setCreatedAt(LocalDateTime.now());

        Question savedQuestion = questionRepository.save(question);
        return convertToDTO(savedQuestion);
    }

    public QuestionDTO update(Long id, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));

        BeanUtils.copyProperties(questionDTO, question, "id", "user", "createdAt");
        Question updatedQuestion = questionRepository.save(question);
        return convertToDTO(updatedQuestion);
    }

    public void delete(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));
        questionRepository.delete(question);
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUserId(question.getUser().getId());
        questionDTO.setUsername(question.getUser().getUsername());
        return questionDTO;
    }
}
