package com.example.helphub.controller;

import com.example.helphub.dto.AnswerDTO;
import com.example.helphub.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/answers")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers() {
        List<AnswerDTO> answers = answerService.findAll();
        if (answers.isEmpty()) {
            return ResponseEntity.noContent().build();  // Returns 204 NO_CONTENT
        }
        return ResponseEntity.ok(answers);  // Returns 200 OK with the list
    }

    @GetMapping("/answers/{id}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable Long id) {
        AnswerDTO answer = answerService.findById(id);
        if (answer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/question/{questionId}")
    public List<AnswerDTO> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.findByQuestionId(questionId);
    }

    @PostMapping("/answers")
    public ResponseEntity<AnswerDTO> createAnswer(@RequestBody AnswerDTO answerDTO) {
        try {
            AnswerDTO savedAnswer = answerService.save(answerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAnswer);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable Long id, @RequestBody AnswerDTO answerDTO) {
        AnswerDTO updatedAnswer = answerService.update(id, answerDTO);
        if (updatedAnswer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        try {
            answerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
