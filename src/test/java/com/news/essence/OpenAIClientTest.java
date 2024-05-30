package com.news.essence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class OpenAIClientTest {
    private Environment env;


    @Test
    void summarize() throws Exception {
        Scanner scanner = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        String articleContent = "Test article content.";
        String expectedSummary = "<p>Test article content.</p>";

        OpenAIClient openAIClient = new OpenAIClient("");

        JsonNode mockJsonNode = objectMapper.createObjectNode()
                .putObject("choices")
                .putArray("choices")
                .addObject()
                .put("text", expectedSummary);

        String mockResponseBody = objectMapper.writeValueAsString(mockJsonNode);

        String summary = openAIClient.summarize(articleContent);
        // Verify the summary
        assertNotNull(summary);
        assertEquals(expectedSummary, summary);
    }
}