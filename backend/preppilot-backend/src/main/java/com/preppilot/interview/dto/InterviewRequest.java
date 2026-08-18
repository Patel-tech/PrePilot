package com.preppilot.interview.dto;

import com.preppilot.interview.entity.InterviewDifficulty;
import com.preppilot.interview.entity.InterviewType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InterviewRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Difficulty is required")
    private InterviewDifficulty difficulty;

    @NotNull(message = "Interview type is required")
    private InterviewType type;

    public InterviewRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public InterviewDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(
            InterviewDifficulty difficulty) {

        this.difficulty = difficulty;
    }

    public InterviewType getType() {
        return type;
    }

    public void setType(
            InterviewType type) {

        this.type = type;
    }
}