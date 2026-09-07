
package com.preppilot.interview.service;

import com.preppilot.authentication.entity.User;
import com.preppilot.authentication.repository.UserRepository;
import com.preppilot.common.exception.ResourceNotFoundException;

import com.preppilot.interview.ai.QuestionGenerationService;
import com.preppilot.interview.dto.*;

import com.preppilot.interview.entity.*;

import com.preppilot.interview.mapper.InterviewMapper;

import com.preppilot.interview.repository.CategoryRepository;
import com.preppilot.interview.repository.InterviewQuestionRepository;
import com.preppilot.interview.repository.InterviewRepository;
import com.preppilot.interview.repository.QuestionRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class InterviewServiceImpl
        implements InterviewService {

    private final InterviewRepository interviewRepository;

    private final CategoryRepository categoryRepository;

    private final InterviewQuestionRepository interviewQuestionRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final InterviewMapper interviewMapper;
    private final QuestionGenerationService questionGenerationService;

    public InterviewServiceImpl(
            InterviewRepository interviewRepository,
            InterviewQuestionRepository
                    interviewQuestionRepository,
            QuestionRepository questionRepository,
            UserRepository userRepository,
            InterviewMapper interviewMapper,
            QuestionGenerationService questionGenerationService,
            CategoryRepository categoryRepository) {

        this.interviewRepository = interviewRepository;

        this.interviewQuestionRepository = interviewQuestionRepository;

        this.questionRepository = questionRepository;

        this.userRepository = userRepository;

        this.interviewMapper = interviewMapper;
        this.questionGenerationService = questionGenerationService;
        this.categoryRepository = categoryRepository;
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
    @Override
    public List<GeneratedQuestionResponse>
    generateQuestions(
            Long interviewId,
            GenerateQuestionsRequest request) {

        Interview interview =
                getInterviewForCurrentUser(interviewId);

        if (interview.getStatus() != InterviewStatus.CREATED) {
            throw new IllegalStateException(
                    "Questions can only be generated "
                            + "for CREATED interviews"
            );
        }

        AiQuestionRequest aiRequest =
                new AiQuestionRequest();

        aiRequest.setTopic(
                request.getTopic()
        );

        aiRequest.setDifficulty(
                interview.getDifficulty().name()
        );

        aiRequest.setInterviewType(
                interview.getType().name()
        );

        aiRequest.setNumberOfQuestions(
                request.getNumberOfQuestions()
        );

        List<AiGeneratedQuestion> generatedQuestions =
                questionGenerationService
                        .generateQuestions(aiRequest);

        validateGeneratedQuestions(
                generatedQuestions
        );

        validateDuplicateQuestions(
                generatedQuestions
        );

        validateExistingQuestions(
                generatedQuestions
        );

        return saveGeneratedQuestions(
                interview,
                request.getTopic(),
                generatedQuestions
        );
    }
    private void validateGeneratedQuestions(
            List<AiGeneratedQuestion> questions) {

        if (questions == null || questions.isEmpty()) {
            throw new IllegalStateException(
                    "AI did not generate any questions"
            );
        }

        for (AiGeneratedQuestion question : questions) {

            if (question == null) {
                throw new IllegalStateException(
                        "AI generated an invalid question"
                );
            }

            if (question.getQuestion() == null
                    || question.getQuestion().isBlank()) {

                throw new IllegalStateException(
                        "AI generated question text is empty"
                );
            }

            if (question.getAnswer() == null
                    || question.getAnswer().isBlank()) {

                throw new IllegalStateException(
                        "AI generated question answer is empty"
                );
            }
        }
    }


    private void validateDuplicateQuestions(
            List<AiGeneratedQuestion> questions) {

        Set<String> questionTexts =
                new HashSet<>();

        for (AiGeneratedQuestion question : questions) {

            String normalized =
                    question.getQuestion()
                            .trim()
                            .toLowerCase();

            if (!questionTexts.add(normalized)) {

                throw new IllegalStateException(
                        "AI generated duplicate questions"
                );
            }
        }
    }

    private  List<GeneratedQuestionResponse> saveGeneratedQuestions(
            Interview interview,
            String topic,
            List<AiGeneratedQuestion> generatedQuestions) {

        Category category =
                resolveCategory(topic);

        List<InterviewQuestion> existingQuestions =
                interviewQuestionRepository
                        .findByInterviewIdOrderByQuestionOrderAsc(
                                interview.getId()
                        );

        int startingOrder =
                existingQuestions.size() + 1;

        List<GeneratedQuestionResponse> responses =
                new ArrayList<>();
        for (int i = 0;
             i < generatedQuestions.size();
             i++) {

            AiGeneratedQuestion generatedQuestion =
                    generatedQuestions.get(i);

            Question question =
                    new Question();

            question.setQuestionText(
                    generatedQuestion.getQuestion()
            );

            question.setExpectedAnswer(
                    generatedQuestion.getAnswer()
            );

            question.setDifficulty(
                    interview.getDifficulty()
            );

            /*
             * Store the actual requested topic.
             *
             * Example:
             * Java Exception Handling
             * Java Collections
             * Java Multithreading
             */
            question.setTechnology(
                    topic.trim()
            );

            /*
             * Associate the question with the
             * parent category.
             *
             * Example:
             * Java Exception Handling -> Java
             */
            question.setCategory(category);

            Question savedQuestion =
                    questionRepository.save(question);

            InterviewQuestion interviewQuestion =
                    new InterviewQuestion();

            interviewQuestion.setInterview(
                    interview
            );

            interviewQuestion.setQuestion(
                    savedQuestion
            );

            interviewQuestion.setQuestionOrder(
                    startingOrder + i
            );
            InterviewQuestion savedInterviewQuestion =
                    interviewQuestionRepository.save(
                            interviewQuestion
                    );
            responses.add(
                    new GeneratedQuestionResponse(
                            savedQuestion.getId(),
                            savedInterviewQuestion.getId(),
                            savedInterviewQuestion.getQuestionOrder(),
                            generatedQuestion.getQuestion(),
                            generatedQuestion.getAnswer(),
                            generatedQuestion.getExplanation(),
                            generatedQuestion.getTags()
                    )
            );

        }
        return responses;
    }

    private Category resolveCategory(String topic) {

        String normalizedTopic =
                topic.trim().toLowerCase();

        if (normalizedTopic.startsWith("java")) {

            return categoryRepository
                    .findByName("Java")
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Java category not found"
                            )
                    );
        }

        if (normalizedTopic.startsWith("spring boot")) {

            return categoryRepository
                    .findByName("Spring Boot")
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Spring Boot category not found"
                            )
                    );
        }

        throw new IllegalStateException(
                "No category mapping found for topic: "
                        + topic
        );
    }

    private void validateExistingQuestions(
            List<AiGeneratedQuestion> questions) {

        for (AiGeneratedQuestion question : questions) {

            boolean exists =
                    questionRepository
                            .existsByQuestionTextIgnoreCase(
                                    question.getQuestion().trim()
                            );

            if (exists) {
                throw new IllegalStateException(
                        "Question already exists: "
                                + question.getQuestion()
                );
            }
        }
    }

}