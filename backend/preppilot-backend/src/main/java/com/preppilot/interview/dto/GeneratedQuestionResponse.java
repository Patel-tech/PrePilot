package com.preppilot.interview.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratedQuestionResponse {

    private Long questionId;

    private Long interviewQuestionId;

    private Integer questionOrder;

    private String question;

    private String answer;

    private String explanation;

    private List<String> tags;

    public GeneratedQuestionResponse() {
    }

    public GeneratedQuestionResponse(
            Long questionId,
            Long interviewQuestionId,
            Integer questionOrder,
            String question,
            String answer,
            String explanation,
            List<String> tags) {

        this.questionId = questionId;
        this.interviewQuestionId =
                interviewQuestionId;
        this.questionOrder =
                questionOrder;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.tags = tags;
    }
}