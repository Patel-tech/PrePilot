package com.preppilot.interview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateQuestionResponse {

    private Long interviewQuestionId;

    private Long questionId;

    private Integer questionOrder;

    private String question;

    private String difficulty;

    private String technology;

    public CandidateQuestionResponse() {
    }

    public CandidateQuestionResponse(
            Long interviewQuestionId,
            Long questionId,
            Integer questionOrder,
            String question,
            String difficulty,
            String technology) {

        this.interviewQuestionId =
                interviewQuestionId;

        this.questionId =
                questionId;

        this.questionOrder =
                questionOrder;

        this.question =
                question;

        this.difficulty =
                difficulty;

        this.technology =
                technology;
    }
}