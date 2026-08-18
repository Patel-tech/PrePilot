package com.preppilot.interview.dto;

import jakarta.validation.constraints.NotNull;

public class InterviewQuestionRequest {

    @NotNull(message = "Question ID is required")
    private Long questionId;

    private Integer questionOrder;

    public InterviewQuestionRequest() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(
            Long questionId) {

        this.questionId = questionId;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(
            Integer questionOrder) {

        this.questionOrder = questionOrder;
    }
}