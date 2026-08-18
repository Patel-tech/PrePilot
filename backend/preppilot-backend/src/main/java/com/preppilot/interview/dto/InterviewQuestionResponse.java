
package com.preppilot.interview.dto;

public class InterviewQuestionResponse {

    private Long id;

    private Long interviewId;

    private Long questionId;

    private Integer questionOrder;

    public InterviewQuestionResponse(
            Long id,
            Long interviewId,
            Long questionId,
            Integer questionOrder) {

        this.id = id;
        this.interviewId = interviewId;
        this.questionId = questionId;
        this.questionOrder = questionOrder;
    }

    public Long getId() {
        return id;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }
}