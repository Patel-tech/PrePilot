package com.preppilot.interview.ai;

import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionGenerationServiceImpl
        implements QuestionGenerationService {

    private static final Logger log =
            LoggerFactory.getLogger(
                    QuestionGenerationServiceImpl.class
            );

    private final QuestionGenerator questionGenerator;

    public QuestionGenerationServiceImpl(
            QuestionGenerator questionGenerator) {

        this.questionGenerator = questionGenerator;
    }

    @Override
    public List<AiGeneratedQuestion> generateQuestions(
            AiQuestionRequest request) {

        log.info("Starting question generation service");

        validateRequest(request);

        log.info("Request validation completed");

        List<AiGeneratedQuestion> questions =
                questionGenerator.generateQuestions(request);

        log.info(
                "AI provider returned {} questions",
                questions.size()
        );

        return questions;
    }

    private void validateRequest(
            AiQuestionRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "AI question request cannot be null"
            );
        }

        if (request.getTopic() == null
                || request.getTopic().isBlank()) {

            throw new IllegalArgumentException(
                    "Topic is required"
            );
        }

        if (request.getNumberOfQuestions() < 1
                || request.getNumberOfQuestions() > 20) {

            throw new IllegalArgumentException(
                    "Number of questions must be between 1 and 20"
            );
        }
    }
}