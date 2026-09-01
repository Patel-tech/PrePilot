package com.preppilot.interview.dto;


import java.util.List;

public class AiGeneratedQuestion {

    private String question;

    private String answer;

    private String explanation;

    private List<String> tags;

    public AiGeneratedQuestion() {
    }

    public AiGeneratedQuestion(
            String question,
            String answer,
            String explanation,
            List<String> tags) {

        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.tags = tags;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public List<String> getTags() {
        return tags;
    }
}
