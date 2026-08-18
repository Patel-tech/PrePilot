package com.preppilot.interview.mapper;

import com.preppilot.interview.dto.InterviewRequest;
import com.preppilot.interview.dto.InterviewResponse;
import com.preppilot.interview.entity.Interview;

public interface InterviewMapper {

    Interview toEntity(
            InterviewRequest request);

    InterviewResponse toResponse(
            Interview interview);
}