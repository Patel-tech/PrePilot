package com.preppilot.interview.ai;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionGenerationServiceImpl implements QuestionGenerationService {

    private final QuestionGenerator questionGenerator;

    public QuestionGenerationServiceImpl(QuestionGenerator questionGenerator) {

        this.questionGenerator = questionGenerator;
    }

    @Override
    public List<AiGeneratedQuestion> generateQuestions(AiQuestionRequest request) {

        validateRequest(request);

        return questionGenerator.generateQuestions(request);
    }

    private void validateRequest(AiQuestionRequest request) {

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

        if (request.getNumberOfQuestions() == null || request.getNumberOfQuestions() <= 0) {

            throw new IllegalArgumentException(
                    "Number of questions must be greater than zero"
            );
        }

        if (request.getNumberOfQuestions() > 20) {

            throw new IllegalArgumentException(
                    "Maximum 20 questions can be generated"
            );
        }
    }
}