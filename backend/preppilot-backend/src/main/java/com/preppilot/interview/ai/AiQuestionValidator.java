package com.preppilot.interview.ai;

import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiQuestionValidator {

    public void validate(
            AiQuestionRequest request,
            List<AiGeneratedQuestion> questions) {

        if (questions == null || questions.isEmpty()) {
            throw new IllegalStateException(
                    "AI did not generate any questions"
            );
        }

        if (questions.size() != request.getNumberOfQuestions()) {
            throw new IllegalStateException(
                    "AI generated "
                            + questions.size()
                            + " questions instead of "
                            + request.getNumberOfQuestions()
            );
        }

        for (AiGeneratedQuestion question : questions) {

            if (question.getQuestion() == null
                    || question.getQuestion().isBlank()) {

                throw new IllegalStateException(
                        "AI generated question is empty"
                );
            }

            if (question.getAnswer() == null
                    || question.getAnswer().isBlank()) {

                throw new IllegalStateException(
                        "AI generated answer is empty"
                );
            }

            if (question.getExplanation() == null
                    || question.getExplanation().isBlank()) {

                throw new IllegalStateException(
                        "AI generated explanation is empty"
                );
            }

            if (question.getTags() == null
                    || question.getTags().isEmpty()) {

                throw new IllegalStateException(
                        "AI generated question has no tags"
                );
            }
        }
    }
}