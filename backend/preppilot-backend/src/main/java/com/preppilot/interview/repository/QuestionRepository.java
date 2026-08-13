package com.preppilot.interview.repository;

import com.preppilot.interview.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    List<Question> findByTechnology(
            String technology);
}
