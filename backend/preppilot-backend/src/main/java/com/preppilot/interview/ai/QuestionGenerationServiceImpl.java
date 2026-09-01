package com.preppilot.interview.ai;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionGenerationServiceImpl
        implements QuestionGenerationService {

    private final QuestionGenerator questionGenerator;
    private final AiQuestionValidator aiQuestionValidator;

    @Override
    public List<AiGeneratedQuestion> generateQuestions(AiQuestionRequest request) {

        // 1. Validate request
        validateRequest(request);

        // 2. Call AI provider
        List<AiGeneratedQuestion> questions = questionGenerator.generateQuestions(request);

        // 3. Validate AI output
        aiQuestionValidator.validate(request, questions);

        // 4. Return generated questions
        return questions;
    }

    private void validateRequest(AiQuestionRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "AI question request cannot be null"
            );
        }

        if (request.getTopic() == null || request.getTopic().isBlank()) {

            throw new IllegalArgumentException(
                    "Topic is required"
            );
        }

        if (request.getNumberOfQuestions() <= 0 || request.getNumberOfQuestions() > 20) {

            throw new IllegalArgumentException(
                    "Number of questions must be between 1 and 20"
            );
        }
    }
}