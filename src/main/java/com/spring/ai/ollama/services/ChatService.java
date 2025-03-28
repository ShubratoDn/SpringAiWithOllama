package com.spring.ai.ollama.services;


import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.stringtemplate.v4.ST;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResults(String prompt){
        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.builder()
                                .model(OllamaModel.MISTRAL)
                                .temperature(0.4)
                                .build()
                ));

        return response.getResult().getOutput().getText();
    }

    public String analyzeImage() {
        try {
            // Load image from classpath (src/main/resources/multimodal.test.png)
            Resource imageResource = new ClassPathResource("multimodal.test.png");

            // Create UserMessage with text + image
            UserMessage userMessage = new UserMessage(
                    "Explain what do you see on this picture?",
                    List.of(new Media(MediaType.IMAGE_PNG, imageResource))
            );

            // Create Prompt with model options
            Prompt prompt = new Prompt(
                    List.of(userMessage),
                    OllamaOptions.builder().model("llava").build()
            );

            // Call chat model
            ChatResponse response = chatModel.call(prompt);

            return response.getResult().getOutput().getText();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
