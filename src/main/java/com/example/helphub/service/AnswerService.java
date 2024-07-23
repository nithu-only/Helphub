package com.example.helphub.service;

import com.example.helphub.dto.AnswerDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Answer;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.repository.AnswerRepository;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AnswerDTO> findAll() {
        return answerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AnswerDTO findById(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + id));
        return convertToDTO(answer);
    }

    public List<AnswerDTO> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AnswerDTO save(AnswerDTO answerDTO) {
        User user = userRepository.findById(answerDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + answerDTO.getUserId()));
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + answerDTO.getQuestionId()));

        Answer answer = new Answer();
        BeanUtils.copyProperties(answerDTO, answer);
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setCreatedAt(LocalDateTime.now());

        Answer savedAnswer = answerRepository.save(answer);
        return convertToDTO(savedAnswer);
    }

    public AnswerDTO update(Long id, AnswerDTO answerDTO) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + id));

        BeanUtils.copyProperties(answerDTO, answer, "id", "user", "question", "createdAt");
        Answer updatedAnswer = answerRepository.save(answer);
        return convertToDTO(updatedAnswer);
    }

    public void delete(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + id));
        answerRepository.delete(answer);
    }

    private AnswerDTO convertToDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        BeanUtils.copyProperties(answer, answerDTO);
        answerDTO.setUserId(answer.getUser().getId());
        answerDTO.setQuestionId(answer.getQuestion().getId());
        answerDTO.setUsername(answer.getUser().getUsername());
        return answerDTO;
    }
}

