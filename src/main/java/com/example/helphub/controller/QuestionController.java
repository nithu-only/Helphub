package com.example.helphub.controller;

import com.example.helphub.dto.QuestionDTO;
import com.example.helphub.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionDTO> getAllQuestions() {
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<QuestionDTO> getQuestionsByUserId(@PathVariable Long userId) {
        return questionService.findByUserId(userId);
    }

    @PostMapping
    public QuestionDTO createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        return questionService.save(questionDTO);
    }

    @PutMapping("/{id}")
    public QuestionDTO updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionDTO questionDTO) {
        return questionService.update(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
    }
}
