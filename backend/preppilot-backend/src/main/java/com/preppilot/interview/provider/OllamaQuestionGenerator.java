package com.preppilot.interview.provider;

import com.preppilot.interview.ai.QuestionGenerator;
import com.preppilot.interview.dto.AiGeneratedQuestion;
import com.preppilot.interview.dto.AiQuestionRequest;
import com.preppilot.interview.ai.Exception.AiGenerationException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("ollama")
public class OllamaQuestionGenerator implements QuestionGenerator {

    private final ChatClient chatClient;

    public OllamaQuestionGenerator(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public List<AiGeneratedQuestion> generateQuestions(
            AiQuestionRequest request) {

        try {

            return chatClient
                    .prompt()

                    .system("""
        You are a senior Java developer and expert technical interviewer.

        Your job is to generate high-quality Java interview questions
        for developers with professional experience.

        STRICT RULES:

        1. Every question must be directly related to the requested topic.

        2. Every answer must directly answer the question.

        3. NEVER use the question title as the answer.

        4. NEVER return answers such as:
           - "Iterator vs Enumeration"
           - "ConcurrentHashMap"
           - "HashMap"
           - "How to implement..."
           - "true"
           - "false"
           - "yes"
           - "no"

           unless the question explicitly requires a single-word answer.

        5. For conceptual questions, provide a complete technical answer.

        6. For comparison questions, explain BOTH sides and clearly
           identify the differences.

        7. For implementation questions, explain the implementation
           approach and include important Java APIs or code concepts.

        8. Answers should normally contain multiple sentences.

        9. Explanations must provide additional educational value.
           Do not simply repeat the answer.

        10. Do not invent Java classes, interfaces, APIs, or frameworks.

        11. Only use standard Java APIs unless the question explicitly
            asks about a third-party library.

        12. Avoid duplicate or nearly duplicate questions.

        13. Match the requested difficulty.

        14. Match the requested interview type.

        15. Generate exactly the requested number of questions.

        16. Tags must be technically relevant.

        17. Return only the requested structured output.
        """).user("""
        Generate exactly %d Java interview questions.

        Topic: %s

        Difficulty: %s

        Interview Type: %s

        Quality requirements:

        - Questions must be suitable for a real technical interview.
        - Answers must demonstrate actual technical understanding.
        - Each answer should normally contain at least 2-4 sentences.
        - Comparison questions must explain the differences explicitly.
        - "How" questions must explain the approach.
        - Implementation questions must explain the important steps.
        - Avoid trivial definition-only questions.
        - Avoid invented Java APIs.
        - Include practical examples when appropriate.
        - Explanations should teach the candidate something beyond
          simply repeating the answer.
        """.formatted(
                   request.getNumberOfQuestions(),
                            request.getTopic(),
                            request.getDifficulty(),
                            request.getInterviewType()
                            ))

                    .call()

                    .entity(
                            new ParameterizedTypeReference<
                                    List<AiGeneratedQuestion>>() {
                            }
                    );

        } catch (Exception ex) {
            ex.printStackTrace();

            throw new AiGenerationException(
                    "Failed to generate interview questions using Ollama: "
                            + ex.getMessage(),
                    ex
            );
        }
    }
}