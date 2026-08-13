package com.preppilot.interview.mapper;

import com.preppilot.interview.dto.QuestionRequest;
import com.preppilot.interview.dto.QuestionResponse;
import com.preppilot.interview.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapperImpl
        implements QuestionMapper {

    @Override
    public Question toEntity(
            QuestionRequest request) {

        Question question = new Question();

        question.setQuestionText(
                request.getQuestionText());

        question.setExpectedAnswer(
                request.getExpectedAnswer());

        question.setDifficulty(
                request.getDifficulty());

        question.setTechnology(
                request.getTechnology());

        return question;
    }

    @Override
    public QuestionResponse toResponse(
            Question question) {

        Long categoryId = null;
        String categoryName = null;

        if (question.getCategory() != null) {

            categoryId =
                    question.getCategory().getId();

            categoryName =
                    question.getCategory().getName();
        }

        return new QuestionResponse(
                question.getId(),
                question.getQuestionText(),
                question.getExpectedAnswer(),
                question.getDifficulty(),
                question.getTechnology(),
                categoryId,
                categoryName
        );
    }
}