package com.preppilot.interview.ai;


import com.preppilot.interview.ai.Exception.AiGenerationException;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class AiQuestionQualityValidator {

    private static final Set<String> INVALID_SHORT_ANSWERS = Set.of(
            "true",
            "false",
            "yes",
            "no",
            "iterator",
            "hashmap",
            "hashset",
            "treemap",
            "treeset",
            "concurrenthashmap"
    );

    public void validate(List<AiGeneratedQuestion> questions) {

        for (AiGeneratedQuestion question : questions) {

            validateQuestion(question);
        }
    }

    private void validateQuestion(
            AiGeneratedQuestion question) {

        if (question == null) {
            throw new AiGenerationException(
                    "AI generated a null question"
            );
        }

        validateQuestionText(question);

        validateAnswer(question);

        validateExplanation(question);

        validateTags(question);
    }

    private void validateQuestionText(
            AiGeneratedQuestion question) {

        if (question.getQuestion() == null
                || question.getQuestion().isBlank()) {

            throw new AiGenerationException(
                    "AI generated an empty question"
            );
        }
    }

    private void validateAnswer(
            AiGeneratedQuestion question) {

        String answer = question.getAnswer();

        if (answer == null || answer.isBlank()) {

            throw new AiGenerationException(
                    "AI generated an empty answer"
            );
        }

        String normalizedAnswer =
                answer.trim().toLowerCase();

        if (INVALID_SHORT_ANSWERS.contains(
                normalizedAnswer)) {

            throw new AiGenerationException(
                    "AI generated an insufficient answer for question: "
                            + question.getQuestion()
            );
        }

        if (answer.trim().length() < 30) {

            throw new AiGenerationException(
                    "AI generated an answer that is too short: "
                            + question.getQuestion()
            );
        }
    }

    private void validateExplanation(
            AiGeneratedQuestion question) {

        String explanation =
                question.getExplanation();

        if (explanation == null
                || explanation.isBlank()) {

            throw new AiGenerationException(
                    "AI generated an empty explanation"
            );
        }

        if (explanation.trim().length() < 50) {

            throw new AiGenerationException(
                    "AI generated an explanation that is too short"
            );
        }
    }

    private void validateTags(
            AiGeneratedQuestion question) {

        List<String> tags = question.getTags();

        if (tags == null || tags.isEmpty()) {

            throw new AiGenerationException(
                    "AI generated question without tags"
            );
        }
    }
}