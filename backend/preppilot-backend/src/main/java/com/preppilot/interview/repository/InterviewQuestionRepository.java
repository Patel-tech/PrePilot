package com.preppilot.interview.repository;

import com.preppilot.interview.entity.InterviewQuestion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findByInterviewIdOrderByQuestionOrder(Long interviewId);

    Optional<InterviewQuestion> findByInterviewIdAndQuestionId(Long interviewId, Long questionId);
}