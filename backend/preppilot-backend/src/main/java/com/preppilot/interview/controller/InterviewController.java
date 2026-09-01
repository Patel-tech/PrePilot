package com.preppilot.interview.controller;

import com.preppilot.interview.dto.*;

import com.preppilot.interview.service.InterviewService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {

        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewResponse>
    createInterview(@Valid @RequestBody InterviewRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        interviewService
                                .createInterview(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getMyInterviews() {

        return ResponseEntity.ok(
                interviewService
                        .getMyInterviews()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService
                        .getInterviewById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponse> updateInterview(@PathVariable Long id, @Valid @RequestBody InterviewRequest request) {

        return ResponseEntity.ok(
                interviewService
                        .updateInterview(
                                id,
                                request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {

        interviewService
                .deleteInterview(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{id}/questions")
    public ResponseEntity<InterviewQuestionResponse>
    addQuestion(
            @PathVariable Long id,
            @Valid
            @RequestBody
            InterviewQuestionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        interviewService
                                .addQuestion(
                                        id,
                                        request)
                );
    }

    @DeleteMapping("/{id}/questions/{questionId}")
    public ResponseEntity<Void> removeQuestion(@PathVariable Long id, @PathVariable Long questionId) {

        interviewService.removeQuestion(
                id,
                questionId);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<InterviewResponse> startInterview(@PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService
                        .startInterview(id)
        );
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<InterviewResponse> completeInterview(@PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService
                        .completeInterview(id)
        );
    }

    @PostMapping("/{id}/generate-questions")
    public ResponseEntity<List<AiGeneratedQuestion>>
    generateQuestions(@PathVariable Long id, @Valid @RequestBody GenerateQuestionsRequest request) {

        return ResponseEntity.ok(
                interviewService
                        .generateQuestions(
                                id,
                                request)
        );
    }
}