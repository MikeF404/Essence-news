package com.news.essence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class OpenAIClientTest {

    @Test
    void summarize() throws Exception {
        Scanner scanner = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        String articleContent = "Test article content.";
        String expectedSummary = "Promotional Summary: This is a test summary.\nDetailed Summary with bullet points:\n* Point 1\n* Point 2";

        OpenAIClient openAIClient = new OpenAIClient(GptApiKey);

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