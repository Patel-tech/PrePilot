package com.preppilot.interview.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiGeneratedQuestion {

    private String question;
//    AiQuestionType questionType;

    private String answer;

    private String explanation;



    private List<String> tags;

    public AiGeneratedQuestion() {
    }

    public AiGeneratedQuestion(
            String question,
//            AiQuestionType questionType,
            String answer,
            String explanation,
            List<String> tags) {

        this.question = question;
//        this.questionType = questionType;
        this.answer = answer;
        this.explanation = explanation;
        this.tags = tags;
    }


//    public String getQuestion() {
//        return question;
//    }
//
//    public String getAnswer() {
//        return answer;
//    }
//
//    public String getExplanation() {
//        return explanation;
//    }
//
//    public List<String> getTags() {
//        return tags;
//    }


}
