package com.totoo.system.tool.AIChat;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatCompletionsClient {

    private static final String API_URL = "https://docs.siliconflow.cn/cn/api-reference/chat-completions/chat-completions";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        try {
            // 构建请求体
            Map<String, Object> requestBody = Map.of(
                    "model", "deepseek-ai/DeepSeek-R1",
                    "messages", List.of(
                            Map.of("role", "user", "content", "Hello, how are you?")
                    )
            );

            // 发送请求
            String response = sendChatCompletionsRequest(requestBody);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendChatCompletionsRequest(Map<String, Object> requestBody) throws Exception {
        // 将请求体转换为 JSON 字符串
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        // 创建 HTTP 客户端
        HttpClient client = HttpClient.newHttpClient();

        // 创建 HTTP 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();

        // 发送请求并获取响应
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        // 返回响应体
        return response.body();
    }
}