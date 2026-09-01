package com.preppilot.interview.dto;

public class AiQuestionRequest {

    private String topic;

    private String difficulty;

    private String interviewType;

    private Integer numberOfQuestions;

    public AiQuestionRequest() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(
            Integer numberOfQuestions) {

        this.numberOfQuestions =
                numberOfQuestions;
    }
}