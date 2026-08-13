package com.preppilot.interview.controller;

import com.preppilot.interview.dto.QuestionRequest;
import com.preppilot.interview.dto.QuestionResponse;
import com.preppilot.interview.service.QuestionService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(
            QuestionService questionService) {

        this.questionService =
                questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse>
    createQuestion(
            @Valid
            @RequestBody
            QuestionRequest request) {

        QuestionResponse response =
                questionService.createQuestion(
                        request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>>
    getAllQuestions() {

        return ResponseEntity.ok(
                questionService.getAllQuestions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse>
    getQuestionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                questionService
                        .getQuestionById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse>
    updateQuestion(
            @PathVariable Long id,
            @Valid
            @RequestBody
            QuestionRequest request) {

        return ResponseEntity.ok(
                questionService.updateQuestion(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteQuestion(
            @PathVariable Long id) {

        questionService.deleteQuestion(id);

        return ResponseEntity.noContent()
                .build();
    }
}