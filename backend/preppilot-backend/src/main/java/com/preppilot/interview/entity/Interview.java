package com.preppilot.interview.entity;

import com.preppilot.authentication.entity.User;
import com.preppilot.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "interviews")
public class Interview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private InterviewDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @Enumerated(EnumType.STRING)
    private InterviewType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @OneToMany(
            mappedBy = "interview",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<InterviewQuestion> questions =
            new ArrayList<>();
}
