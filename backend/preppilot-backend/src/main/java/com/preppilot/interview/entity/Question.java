package com.preppilot.interview.entity;

import com.preppilot.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String questionText;

    @Column(columnDefinition = "TEXT")
    private String expectedAnswer;

    @Enumerated(EnumType.STRING)
    private InterviewDifficulty difficulty;

    private String technology;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "question")
    private List<InterviewQuestion> interviews =
            new ArrayList<>();
}