package com.preppilot.interview.service;

import com.preppilot.interview.dto.InterviewQuestionRequest;
import com.preppilot.interview.dto.InterviewRequest;
import com.preppilot.interview.dto.InterviewQuestionResponse;
import com.preppilot.interview.dto.InterviewResponse;

import java.util.List;

public interface InterviewService {

    InterviewResponse createInterview(InterviewRequest request);

    List<InterviewResponse> getMyInterviews();

    InterviewResponse getInterviewById(Long id);

    InterviewResponse updateInterview(Long id, InterviewRequest request);

    void deleteInterview(Long id);

    InterviewQuestionResponse addQuestion(Long interviewId, InterviewQuestionRequest request);

    void removeQuestion(Long interviewId, Long questionId);

    InterviewResponse startInterview(Long id);

    InterviewResponse completeInterview(Long id);
}