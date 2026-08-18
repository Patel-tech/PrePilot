
package com.preppilot.interview.service;

import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.repository.UserRepository;
import com.preppilot.common.exception.ResourceNotFoundException;

import com.preppilot.interview.dto.InterviewQuestionRequest;
import com.preppilot.interview.dto.InterviewRequest;

import com.preppilot.interview.dto.InterviewQuestionResponse;
import com.preppilot.interview.dto.InterviewResponse;

import com.preppilot.interview.entity.Interview;
import com.preppilot.interview.entity.InterviewQuestion;
import com.preppilot.interview.entity.InterviewStatus;
import com.preppilot.interview.entity.Question;

import com.preppilot.interview.mapper.InterviewMapper;

import com.preppilot.interview.repository.InterviewQuestionRepository;
import com.preppilot.interview.repository.InterviewRepository;
import com.preppilot.interview.repository.QuestionRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterviewServiceImpl
        implements InterviewService {

    private final InterviewRepository interviewRepository;

    private final InterviewQuestionRepository interviewQuestionRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final InterviewMapper interviewMapper;

    public InterviewServiceImpl(
            InterviewRepository interviewRepository,
            InterviewQuestionRepository
                    interviewQuestionRepository,
            QuestionRepository questionRepository,
            UserRepository userRepository,
            InterviewMapper interviewMapper) {

        this.interviewRepository = interviewRepository;

        this.interviewQuestionRepository = interviewQuestionRepository;

        this.questionRepository = questionRepository;

        this.userRepository = userRepository;

        this.interviewMapper = interviewMapper;
    }

    @Override
    public InterviewResponse createInterview(InterviewRequest request) {

        User currentUser = getCurrentUser();

        Interview interview = interviewMapper.toEntity(request);

        interview.setUser(currentUser);

        interview.setStatus(InterviewStatus.CREATED);

        Interview savedInterview = interviewRepository.save(interview);

        return interviewMapper.toResponse(savedInterview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewResponse> getMyInterviews() {

        User currentUser = getCurrentUser();

        return interviewRepository
                .findByUserId(currentUser.getId())
                .stream()
                .map(interviewMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InterviewResponse getInterviewById(Long id) {

        Interview interview = getInterviewForCurrentUser(id);

        return interviewMapper.toResponse(interview);
    }

    @Override
    public InterviewResponse updateInterview(Long id, InterviewRequest request) {

        Interview interview = getInterviewForCurrentUser(id);

        if (interview.getStatus() != InterviewStatus.CREATED) {

            throw new IllegalStateException(
                    "Only CREATED interviews can be updated"
            );
        }

        interview.setTitle(request.getTitle());

        interview.setDescription(request.getDescription());

        interview.setDifficulty(request.getDifficulty());

        interview.setType(request.getType());

        Interview updatedInterview = interviewRepository.save(interview);

        return interviewMapper.toResponse(updatedInterview);
    }

    @Override
    public void deleteInterview(Long id) {

        Interview interview = getInterviewForCurrentUser(id);

        if (interview.getStatus() != InterviewStatus.CREATED) {

            throw new IllegalStateException(
                    "Only CREATED interviews can be deleted"
            );
        }

        interviewRepository.delete(interview);
    }

    @Override
    public InterviewQuestionResponse addQuestion(Long interviewId, InterviewQuestionRequest request) {

        Interview interview = getInterviewForCurrentUser(interviewId);

        if (interview.getStatus() != InterviewStatus.CREATED) {

            throw new IllegalStateException(
                    "Questions can only be added "
                            + "to CREATED interviews"
            );
        }

        Question question =
                questionRepository
                        .findById(
                                request.getQuestionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Question not found with id: "
                                                + request.getQuestionId()
                                )
                        );

        boolean alreadyExists =
                interviewQuestionRepository
                        .findByInterviewIdAndQuestionId(
                                interviewId,
                                question.getId())
                        .isPresent();

        if (alreadyExists) {

            throw new IllegalStateException(
                    "Question is already added "
                            + "to this interview"
            );
        }

        InterviewQuestion interviewQuestion = new InterviewQuestion();

        interviewQuestion.setInterview(interview);

        interviewQuestion.setQuestion(question);

        interviewQuestion.setQuestionOrder(request.getQuestionOrder());

        InterviewQuestion saved = interviewQuestionRepository.save(interviewQuestion);

        return new InterviewQuestionResponse(
                saved.getId(),
                interview.getId(),
                question.getId(),
                saved.getQuestionOrder()
        );
    }

    @Override
    public void removeQuestion(Long interviewId, Long questionId) {

        Interview interview = getInterviewForCurrentUser(interviewId);

        if (interview.getStatus() != InterviewStatus.CREATED) {

            throw new IllegalStateException(
                    "Questions can only be removed "
                            + "from CREATED interviews"
            );
        }

        InterviewQuestion interviewQuestion =
                interviewQuestionRepository
                        .findByInterviewIdAndQuestionId(
                                interviewId,
                                questionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Question is not assigned "
                                                + "to this interview"
                                )
                        );

        interviewQuestionRepository.delete(interviewQuestion);
    }

    @Override
    public InterviewResponse startInterview(Long id) {

        Interview interview = getInterviewForCurrentUser(id);

        if (interview.getStatus() != InterviewStatus.CREATED) {

            throw new IllegalStateException(
                    "Interview cannot be started "
                            + "from current status"
            );
        }

        if (interview.getQuestions() == null || interview.getQuestions().isEmpty()) {

            throw new IllegalStateException(
                    "Interview must have at least "
                            + "one question"
            );
        }

        interview.setStatus(InterviewStatus.IN_PROGRESS);

        Interview saved = interviewRepository.save(interview);

        return interviewMapper.toResponse(saved);
    }

    @Override
    public InterviewResponse completeInterview(Long id) {

        Interview interview = getInterviewForCurrentUser(id);

        if (interview.getStatus() != InterviewStatus.IN_PROGRESS) {

            throw new IllegalStateException(
                    "Only IN_PROGRESS interviews "
                            + "can be completed"
            );
        }

        interview.setStatus(InterviewStatus.COMPLETED);

        Interview saved = interviewRepository.save(interview);

        return interviewMapper.toResponse(saved);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated user not found"
                        )
                );
    }

    private Interview getInterviewForCurrentUser(Long id) {

        User currentUser = getCurrentUser();

        return interviewRepository
                .findById(id)
                .filter(interview ->
                        interview.getUser()
                                .getId()
                                .equals(currentUser.getId()))
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interview not found"
                        )
                );
    }
}