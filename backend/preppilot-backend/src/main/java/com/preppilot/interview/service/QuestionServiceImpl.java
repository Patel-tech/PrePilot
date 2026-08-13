package com.preppilot.interview.service;

import com.preppilot.common.exception.ResourceNotFoundException;
import com.preppilot.interview.dto.QuestionRequest;
import com.preppilot.interview.dto.QuestionResponse;
import com.preppilot.interview.entity.Category;
import com.preppilot.interview.entity.Question;
import com.preppilot.interview.mapper.QuestionMapper;
import com.preppilot.interview.repository.CategoryRepository;
import com.preppilot.interview.repository.QuestionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl
        implements QuestionService {

    private final QuestionRepository questionRepository;

    private final CategoryRepository categoryRepository;

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            CategoryRepository categoryRepository,
            QuestionMapper questionMapper) {

        this.questionRepository =
                questionRepository;

        this.categoryRepository =
                categoryRepository;

        this.questionMapper =
                questionMapper;
    }

    @Override
    public QuestionResponse createQuestion(
            QuestionRequest request) {

        Category category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found with id: "
                                                + request.getCategoryId()
                                )
                        );

        Question question =
                questionMapper.toEntity(request);

        question.setCategory(category);

        Question savedQuestion =
                questionRepository.save(question);

        return questionMapper.toResponse(
                savedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllQuestions() {

        return questionRepository
                .findAll()
                .stream()
                .map(questionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponse getQuestionById(
            Long id) {

        Question question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Question not found with id: "
                                                + id
                                )
                        );

        return questionMapper.toResponse(
                question);
    }

    @Override
    public QuestionResponse updateQuestion(
            Long id,
            QuestionRequest request) {

        Question question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Question not found with id: "
                                                + id
                                )
                        );

        Category category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found with id: "
                                                + request.getCategoryId()
                                )
                        );

        question.setQuestionText(
                request.getQuestionText());

        question.setExpectedAnswer(
                request.getExpectedAnswer());

        question.setDifficulty(
                request.getDifficulty());

        question.setTechnology(
                request.getTechnology());

        question.setCategory(category);

        Question updatedQuestion =
                questionRepository.save(question);

        return questionMapper.toResponse(
                updatedQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {

        Question question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Question not found with id: "
                                                + id
                                )
                        );

        questionRepository.delete(question);
    }
}