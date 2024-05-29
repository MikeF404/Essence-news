package com.news.essence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import java.io.IOException;

public class OpenAIClient {

    private final String apiKey;

    public OpenAIClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String summarize(String content) throws IOException, ParseException {
        String url = "https://api.openai.com/v1/chat/completions";
        String prompt = "Please summarize the following article content in 1-2 sentences that promote the user to open the article, and provide a summary with bullet points:\n\n"
                + content + "\n\nPromotional Summary:\nDetailed Summary with bullet points:\n";

        // Construct the JSON request payload
        String jsonPayload = "{"
                + "\"prompt\": \"" + prompt.replace("\"", "\\\"") + "\","
                + "\"max_tokens\": 500,"
                + "\"n\": 1,"
                + "\"stop\": null,"
                + "\"temperature\": 0.5"
                + "}";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonPayload));

            HttpClientResponseHandler<String> responseHandler = response -> {
                int status = response.getCode();
                if (status >= 200 && status < 300) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    return jsonNode.get("choices").get(0).get("text").asText();
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            return httpClient.execute(httpPost, responseHandler);
        }
    }
}
