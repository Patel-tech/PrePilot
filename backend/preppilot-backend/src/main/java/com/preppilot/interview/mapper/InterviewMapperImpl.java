package com.preppilot.interview.mapper;

import com.preppilot.interview.dto.InterviewRequest;
import com.preppilot.interview.dto.InterviewResponse;
import com.preppilot.interview.entity.Interview;

import org.springframework.stereotype.Component;

@Component
public class InterviewMapperImpl
        implements InterviewMapper {

    @Override
    public Interview toEntity(
            InterviewRequest request) {

        Interview interview =
                new Interview();

        interview.setTitle(
                request.getTitle());

        interview.setDescription(
                request.getDescription());

        interview.setDifficulty(
                request.getDifficulty());

        interview.setType(
                request.getType());

        return interview;
    }

    @Override
    public InterviewResponse toResponse(
            Interview interview) {

        Long userId = null;

        if (interview.getUser() != null) {
            userId =
                    interview.getUser().getId();
        }

        int questionCount =
                interview.getQuestions() == null
                        ? 0
                        : interview.getQuestions().size();

        return new InterviewResponse(
                interview.getId(),
                interview.getTitle(),
                interview.getDescription(),
                interview.getDifficulty(),
                interview.getStatus(),
                interview.getType(),
                userId,
                questionCount
        );
    }
}