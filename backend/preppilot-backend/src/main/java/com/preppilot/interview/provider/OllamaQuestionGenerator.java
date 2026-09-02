package com.preppilot.interview.provider;

import com.preppilot.interview.ai.Exception.AiGenerationException;
import com.preppilot.interview.ai.QuestionGenerator;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("ollama")
public class OllamaQuestionGenerator
        implements QuestionGenerator {

    private static final Logger log =
            LoggerFactory.getLogger(
                    OllamaQuestionGenerator.class
            );

    private final ChatClient chatClient;

    public OllamaQuestionGenerator(
            ChatClient.Builder chatClientBuilder) {

        this.chatClient =
                chatClientBuilder.build();
    }

    @Override
    public List<AiGeneratedQuestion> generateQuestions(
            AiQuestionRequest request) {

        log.info("Starting Ollama question generation...");
        log.info("Topic: {}", request.getTopic());
        log.info(
                "Number of questions: {}",
                request.getNumberOfQuestions()
        );

        String prompt = """
                You are a senior Java developer and technical interviewer.

                Generate exactly %d Java interview questions.

                TOPIC:
                %s

                DIFFICULTY:
                %s

                INTERVIEW TYPE:
                %s


                IMPORTANT FIELD DEFINITIONS:

                Each generated object MUST contain exactly these fields:

                1. "question"
                   - This MUST contain the actual interview question.
                   - It must be a question that can be asked to a candidate.
                   - NEVER put the topic name in this field.

                2. "answer"
                   - This MUST contain the complete correct answer
                     to the "question" field.
                   - NEVER put the question itself in this field.
                   - NEVER put only a short keyword such as "HashMap".

                3. "explanation"
                   - This MUST contain an additional clear technical
                     explanation of the answer.
                   - It should help an interviewer understand why
                     the answer is correct.

                4. "tags"
                   - This MUST contain relevant topic/concept tags.
                   - Examples:
                     ["Java Collections", "Set", "List"]
                     ["HashMap", "Hashing", "Java Collections"]
                   - DO NOT put difficulty in tags.
                   - DO NOT put interview type in tags.
                   - DO NOT put "MEDIUM", "EASY", "HARD",
                     "TECHNICAL", or "HR" in tags.


                STRICT RULES:

                - Generate exactly %d questions.
                - Every question must be about "%s".
                - Every question must match the requested difficulty.
                - Every question must match the requested interview type.
                - Every answer must answer its corresponding question.
                - Never swap question and answer.
                - Never use the topic as the question.
                - Never use the question as the answer.
                - Do not invent Java classes, interfaces, methods,
                  APIs, or frameworks.
                - Do not generate duplicate questions.
                - Use technically accurate Java terminology.


                OUTPUT FORMAT:

                Return ONLY a JSON array.

                The JSON must have exactly this structure:

                [
                  {
                    "question": "actual interview question",
                    "answer": "complete answer to the question",
                    "explanation": "technical explanation of the answer",
                    "tags": [
                      "relevant topic",
                      "relevant concept"
                    ]
                  }
                ]


                EXAMPLE:

                [
                  {
                    "question": "What is the difference between a List and a Set in Java?",
                    "answer": "A List is an ordered collection that allows duplicate elements and provides positional access using an index. A Set is a collection that does not allow duplicate elements. Common List implementations include ArrayList and LinkedList, while common Set implementations include HashSet, LinkedHashSet, and TreeSet.",
                    "explanation": "The main difference is how duplicates and ordering are handled. Lists maintain element positions and can contain duplicates, while Sets enforce uniqueness. The exact ordering behavior depends on the implementation.",
                    "tags": [
                      "Java Collections",
                      "List",
                      "Set"
                    ]
                  }
                ]

                Now generate the requested questions.
                """.formatted(
                request.getNumberOfQuestions(),
                request.getTopic(),
                request.getDifficulty(),
                request.getInterviewType(),
                request.getNumberOfQuestions(),
                request.getTopic()
        );

        log.info("Prompt created. Calling Ollama...");

        long startTime =
                System.currentTimeMillis();

        try {

            List<AiGeneratedQuestion> questions =
                    chatClient
                            .prompt()
                            .user(prompt)
                            .call()
                            .entity(
                                    new ParameterizedTypeReference<
                                            List<AiGeneratedQuestion>>() {
                                    }
                            );


            if (questions == null || questions.isEmpty()) {

                log.error("Ollama returned no questions");

                throw new AiGenerationException(
                        "Ollama returned no interview questions"
                );
            }
            if (questions.size() != request.getNumberOfQuestions()) {

                log.warn(
                        "Ollama returned {} questions, expected {}",
                        questions.size(),
                        request.getNumberOfQuestions()
                );
            }

            long duration =
                    System.currentTimeMillis()
                            - startTime;

            log.info(
                    "Ollama response received in {} ms",
                    duration
            );

            log.info(
                    "Generated {} questions",
                    questions != null
                            ? questions.size()
                            : 0
            );

            return questions;

        } catch (Exception ex) {

            log.error(
                    "Ollama question generation failed",
                    ex
            );

            throw new AiGenerationException(
                    "Failed to generate interview questions using Ollama: "
                            + ex.getMessage(),
                    ex
            );
        }
    }
}