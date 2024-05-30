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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public String summarize(String content) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String prompt = "Summarize the news article that enters after. If possible, include bullet points. Try to keep it simple and engaging. The output must be only the summary, and it must be formatted in HTML tags. No header or anything needed, just the summary with all the necessary markup tags: <h1>, <br>, <ul>, and so on. Do not include title.";

        // Construct the JSON request payload
        String jsonPayload = "{"
                + "\"model\": \"gpt-4o\","
                + "\"messages\": ["
                + "    {\"role\": \"system\", \"content\": [{\"type\": \"text\", \"text\": \"" + prompt.replace("\"", "\\\"") + "\"}]},"
                + "    {\"role\": \"user\", \"content\": [{\"type\": \"text\", \"text\": \"" + content.replace("\"", "\\\"") + "\"}]}"
                + "],"
                + "\"temperature\": 1,"
                + "\"max_tokens\": 256,"
                + "\"top_p\": 1,"
                + "\"frequency_penalty\": 0,"
                + "\"presence_penalty\": 0"
                + "}";

        logger.info("Sending request to OpenAI API with payload: {}", jsonPayload);

        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonPayload, StandardCharsets.UTF_8));

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