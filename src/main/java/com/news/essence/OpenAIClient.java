package com.news.essence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.ParseException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OpenAIClient {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIClient.class);

    private final String apiKey;

    public OpenAIClient(String apiKey) {
        this.apiKey = apiKey;
    }

    protected CloseableHttpClient createHttpClient() {
        return HttpClients.createDefault();
    }

    public String summarize(String content) throws IOException, JSONException {
        String url = "https://api.openai.com/v1/chat/completions";
        String prompt = "Write a concise summary for the following article. If possible, include bullet points to highlight key points. The output must be formatted in HTML tags. Important guidelines: 1. Do not include the article title or any headers. 2. Do not use the word 'summary'; only include the summary content. 3. Avoid unnecessary empty lines. 4. Include a paragraph of text, followed by a couple of bullet points, and conclude with another paragraph of text. Ensure the summary is simple, engaging, and well-formatted.";

        // Construct the JSON request payload
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("model", "gpt-4o");
        jsonPayload.put("temperature", 1);
        jsonPayload.put("max_tokens", 350);
        jsonPayload.put("top_p", 1);
        jsonPayload.put("frequency_penalty", 0);
        jsonPayload.put("presence_penalty", 0);

        // Create messages array
        JSONArray messages = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", prompt);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", content);

        messages.put(systemMessage);
        messages.put(userMessage);

        jsonPayload.put("messages", messages);

        logger.info("Sending request to OpenAI API with payload: {}", jsonPayload.toString());

        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonPayload.toString(), StandardCharsets.UTF_8));

            HttpClientResponseHandler<String> responseHandler = response -> {
                int status = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                logger.info("Received response from OpenAI API: Status Code = {}, Response Body = {}", status, responseBody);

                if (status >= 200 && status < 300) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    return jsonNode.get("choices").get(0).get("message").get("content").asText();
                } else {
                    throw new IOException("Unexpected response status: " + status);
                }
            };
            return httpClient.execute(httpPost, responseHandler);
        }
    }
}
