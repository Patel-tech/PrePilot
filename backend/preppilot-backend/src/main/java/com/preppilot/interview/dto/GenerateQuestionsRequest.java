package com.preppilot.interview.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GenerateQuestionsRequest {

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotNull(message = "Number of questions is required")
    @Min(value = 1,
            message = "At least 1 question is required")
    @Max(value = 20,
            message = "Maximum 20 questions allowed")
    private Integer numberOfQuestions;

    public GenerateQuestionsRequest() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {

        this.numberOfQuestions = numberOfQuestions;
    }
}