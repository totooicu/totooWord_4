package com.totoo.system.tool.AIChat;

import kong.unirest.Unirest;

import java.net.http.HttpResponse;

public class test2 {
    public static void main(String[] args) {
        HttpResponse<String> response = (HttpResponse<String>) Unirest.post("https://api.siliconflow.cn/v1/chat/completions")
                .header("Authorization", "Bearer sk-oqtjzwukmqvmjccwmioaimnjuavjekslvvanjavdgscrglcz")
                .header("Content-Type", "application/json")
                .body("{\n  \"model\": \"deepseek-ai/DeepSeek-V3\",\n  " +
                        "\"messages\": [\n    {\n      \"role\": \"user\",\n      \"content\": \"中国大模型行业2025年将会迎来哪些机遇和挑战？\"\n    }\n  ],\n  \"stream\": false,\n  \"max_tokens\": 512,\n  \"stop\": [\n    \"null\"\n  ],\n  \"temperature\": 0.7,\n  \"top_p\": 0.7,\n  \"top_k\": 50,\n  \"frequency_penalty\": 0.5,\n  \"n\": 1,\n  \"response_format\": {\n    \"type\": \"text\"\n  },\n  \"tools\": [\n    {\n      \"type\": \"function\",\n      \"function\": {\n        \"description\": \"<string>\",\n        \"name\": \"<string>\",\n        \"parameters\": {},\n        \"strict\": false\n      }\n    }\n  ]\n}")
                .asString();
    }
}
