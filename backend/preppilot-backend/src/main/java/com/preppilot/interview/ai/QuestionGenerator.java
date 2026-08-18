package com.preppilot.interview.ai;

import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;

import java.util.List;

public interface QuestionGenerator {

    List<AiGeneratedQuestion> generateQuestions(
            AiQuestionRequest request);
}
