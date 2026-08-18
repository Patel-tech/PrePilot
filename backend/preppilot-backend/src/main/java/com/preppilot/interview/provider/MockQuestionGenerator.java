package com.preppilot.interview.provider;


import com.preppilot.interview.ai.QuestionGenerator;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("mock-ai")
public class MockQuestionGenerator implements QuestionGenerator {

    @Override
    public List<AiGeneratedQuestion> generateQuestions(AiQuestionRequest request) {

        List<AiGeneratedQuestion> questions = new ArrayList<>();

        int count = request.getNumberOfQuestions();

        for (int i = 1; i <= count; i++) {

            questions.add(
                    new AiGeneratedQuestion(
                            "Explain " +
                                    request.getTopic()
                                    + " concept " + i,

                            "Sample answer for "
                                    + request.getTopic(),

                            "This is a mock AI "
                                    + "explanation.",

                            List.of(
                                    request.getTopic(),
                                    request.getDifficulty()
                            )
                    )
            );
        }

        return questions;
    }
}