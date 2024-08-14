package com.example.helphub.controller;


import com.example.helphub.dto.*;
import com.example.helphub.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

//  Admin access to the Users

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/users/username/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/users/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

//  Admin access to the Questions

    @GetMapping("/question")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.findAll();
    }

    @GetMapping("/question/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @GetMapping("/question/user/{userId}")
    public List<QuestionDTO> getQuestionsByUserId(@PathVariable Long userId) {
        return questionService.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
    }


//  Admin access to the Answer of the Questions

    @GetMapping("/answer")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers() {
        List<AnswerDTO> answers = answerService.findAll();
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable Long id) {
        AnswerDTO answers = answerService.findById(id);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/answer/questionId/{questionId}")
    public List<AnswerDTO> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.findByQuestionId(questionId);
    }

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.delete(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }


//  Admin access to the Answer of the Comments


    @GetMapping("/comments")
    public List<CommentDTO> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/comments/{id}")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @GetMapping("/comments/answer/{answerId}")
    public List<CommentDTO> getCommentsByAnswerId(@PathVariable Long answerId) {
        return commentService.findByAnswerId(answerId);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }


//  Admin access to the Answer of the Tags


    @GetMapping("/tags")
    public List<TagDTO> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/tags/{id}")
    public TagDTO getTagById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @DeleteMapping("/tags/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.delete(id);
    }



}
