//package com.example.helphub.controller;
//
//import com.example.helphub.dto.QuestionTagDTO;
//import com.example.helphub.service.QuestionTagService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/question-tags")
//public class QuestionTagController {
//
//    @Autowired
//    private QuestionTagService questionTagService;
//
//    @GetMapping
//    public List<QuestionTagDTO> getAllQuestionTags() {
//        return questionTagService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public QuestionTagDTO getQuestionTagById(@PathVariable Long id) {
//        return questionTagService.findById(id);
//    }
//
//    @PostMapping
//    public QuestionTagDTO createQuestionTag(@Valid @RequestBody QuestionTagDTO questionTagDTO) {
//        return questionTagService.save(questionTagDTO);
//    }
//
//    @PutMapping("/{id}")
//    public QuestionTagDTO updateQuestionTag(@PathVariable Long id, @Valid @RequestBody QuestionTagDTO questionTagDTO) {
//        return questionTagService.update(id, questionTagDTO);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteQuestionTag(@PathVariable Long id) {
//        questionTagService.delete(id);
//    }
//}
//



package com.example.helphub.controller;

import com.example.helphub.dto.QuestionTagDTO;
import com.example.helphub.service.QuestionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/question-tags")
public class QuestionTagController {

    @Autowired
    private QuestionTagService questionTagService;

    @GetMapping
    public List<QuestionTagDTO> getAllQuestionTags() {
        return questionTagService.findAll();
    }

    @GetMapping("/{id}")
    public QuestionTagDTO getQuestionTagById(@PathVariable Long id) {
        return questionTagService.findById(id);
    }

    @PostMapping
    public QuestionTagDTO createQuestionTag(@Valid @RequestBody QuestionTagDTO questionTagDTO) {
        return questionTagService.save(questionTagDTO);
    }

    @PutMapping("/{id}")
    public QuestionTagDTO updateQuestionTag(@PathVariable Long id, @Valid @RequestBody QuestionTagDTO questionTagDTO) {
        return questionTagService.update(id, questionTagDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionTag(@PathVariable Long id) {
        questionTagService.delete(id);
    }
}
