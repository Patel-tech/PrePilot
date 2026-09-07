package com.preppilot.interview.dto;



import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InterviewQuestionDetailResponse {

    private Long interviewQuestionId;

    private Long questionId;

    private Integer questionOrder;

    private String question;

    private String expectedAnswer;

    private String difficulty;

    private String technology;

    public InterviewQuestionDetailResponse() {
    }

    public InterviewQuestionDetailResponse(
            Long interviewQuestionId,
            Long questionId,
            Integer questionOrder,
            String question,
            String expectedAnswer,
            String difficulty,
            String technology) {

        this.interviewQuestionId = interviewQuestionId;
        this.questionId = questionId;
        this.questionOrder = questionOrder;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
        this.difficulty = difficulty;
        this.technology = technology;
    }
}