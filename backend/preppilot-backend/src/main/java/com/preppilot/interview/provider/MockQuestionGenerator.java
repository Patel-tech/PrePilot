package com.preppilot.interview.provider;

import com.preppilot.interview.ai.QuestionGenerator;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionType;
import com.preppilot.interview.dto.AiQuestionRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("mock-ai")
public class MockQuestionGenerator implements QuestionGenerator {

    @Override
    public List<AiGeneratedQuestion> generateQuestions(
            AiQuestionRequest request) {

        return List.of(

                new AiGeneratedQuestion(
                        "What is the difference between HashSet and TreeSet in Java?",

                        "HashSet stores elements using hashing and does not guarantee sorted order, while TreeSet stores elements in sorted order using a tree-based data structure. HashSet generally provides constant-time average performance for basic operations, whereas TreeSet provides logarithmic-time performance.",
                        "This question tests the candidate's understanding of different Set implementations, ordering behavior, internal data structures, and performance.",
                        List.of(
                                "Java",
                                "Collections",
                                "HashSet",
                                "TreeSet"
                        )
                ),

                new AiGeneratedQuestion(
                        "What is the difference between Iterator and Enumeration?",

                        "Iterator is the modern interface used to traverse elements in Java collections, while Enumeration is a legacy interface primarily used with older collection classes such as Vector. Iterator provides hasNext(), next(), and remove(), whereas Enumeration provides hasMoreElements() and nextElement().",
                        "This question tests knowledge of Java collection traversal APIs and the evolution from legacy APIs to the modern Collections Framework.",
                        List.of(
                                "Java",
                                "Collections",
                                "Iterator",
                                "Enumeration"
                        )
                ),

                new AiGeneratedQuestion(
                        "What exception can occur when a collection is structurally modified while it is being iterated?",

                        "ConcurrentModificationException can occur when a collection is structurally modified while it is being iterated using a fail-fast iterator. For example, modifying an ArrayList directly while iterating over it can cause this exception.",
                        "This question checks whether the candidate understands fail-fast iteration and structural modification of collections.",
                        List.of(
                                "Java",
                                "Collections",
                                "Exception Handling"
                        )
                )
        );
    }
}