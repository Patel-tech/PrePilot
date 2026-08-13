package com.preppilot.interview.dto;

import com.preppilot.interview.entity.InterviewDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionRequest {

    @NotBlank(message = "Question text is required")
    private String questionText;

    private String expectedAnswer;

    @NotNull(message = "Difficulty is required")
    private InterviewDifficulty difficulty;

    @NotBlank(message = "Technology is required")
    private String technology;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    public QuestionRequest() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public InterviewDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(
            InterviewDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}