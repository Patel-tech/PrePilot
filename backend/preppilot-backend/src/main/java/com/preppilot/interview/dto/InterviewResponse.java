package com.preppilot.interview.dto;

import com.preppilot.interview.entity.InterviewDifficulty;
import com.preppilot.interview.entity.InterviewStatus;
import com.preppilot.interview.entity.InterviewType;

public class InterviewResponse {

    private Long id;

    private String title;

    private String description;

    private InterviewDifficulty difficulty;

    private InterviewStatus status;

    private InterviewType type;

    private Long userId;

    private Integer questionCount;

    public InterviewResponse() {
    }

    public InterviewResponse(
            Long id,
            String title,
            String description,
            InterviewDifficulty difficulty,
            InterviewStatus status,
            InterviewType type,
            Long userId,
            Integer questionCount) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.status = status;
        this.type = type;
        this.userId = userId;
        this.questionCount = questionCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public InterviewDifficulty getDifficulty() {
        return difficulty;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public InterviewType getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }
}