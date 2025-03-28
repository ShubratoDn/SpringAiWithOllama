package com.spring.ai.ollama.controllers;

import com.spring.ai.ollama.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        return chatService.generateResults(prompt);
    }
}
