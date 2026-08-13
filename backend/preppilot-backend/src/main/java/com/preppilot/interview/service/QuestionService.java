package com.preppilot.interview.service;

import com.preppilot.interview.dto.QuestionRequest;
import com.preppilot.interview.dto.QuestionResponse;

import java.util.List;

public interface QuestionService {

    QuestionResponse createQuestion(
            QuestionRequest request);

    List<QuestionResponse> getAllQuestions();

    QuestionResponse getQuestionById(
            Long id);

    QuestionResponse updateQuestion(
            Long id,
            QuestionRequest request);

    void deleteQuestion(Long id);
}