package com.preppilot.interview.dto;

import com.preppilot.interview.entity.InterviewDifficulty;

public class QuestionResponse {

    private Long id;

    private String questionText;

    private String expectedAnswer;

    private InterviewDifficulty difficulty;

    private String technology;

    private Long categoryId;

    private String categoryName;

    public QuestionResponse() {
    }

    public QuestionResponse(
            Long id,
            String questionText,
            String expectedAnswer,
            InterviewDifficulty difficulty,
            String technology,
            Long categoryId,
            String categoryName) {

        this.id = id;
        this.questionText = questionText;
        this.expectedAnswer = expectedAnswer;
        this.difficulty = difficulty;
        this.technology = technology;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public InterviewDifficulty getDifficulty() {
        return difficulty;
    }

    public String getTechnology() {
        return technology;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}