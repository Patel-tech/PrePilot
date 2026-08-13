package com.preppilot.interview.mapper;

import com.preppilot.interview.dto.QuestionRequest;
import com.preppilot.interview.dto.QuestionResponse;
import com.preppilot.interview.entity.Question;

public interface QuestionMapper {

    Question toEntity(QuestionRequest request);

    QuestionResponse toResponse(Question question);
}