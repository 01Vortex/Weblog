package com.quanxiaoha.weblog.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.quanxiaoha.weblog.web.dto.AiRequest;
import com.quanxiaoha.weblog.web.dto.AiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Value("${bailian.api.key}")
    private String DASHSCOPE_API_KEY;

    private static final String DASHSCOPE_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

    @PostMapping("/ask")
    public ResponseEntity<AiResponse> ask(@RequestBody AiRequest aiRequest) {
        try {
            String answer = callQwen(aiRequest.getQuestion());
            return ResponseEntity.ok(new AiResponse(answer));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new AiResponse("AI 服务调用失败: " + e.getMessage()));
        }
    }

    private String callQwen(String userMessage) throws Exception {
        URL url = new URL(DASHSCOPE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置请求头
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + DASHSCOPE_API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // 构造请求体
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode messages = mapper.createArrayNode();
        messages.add(mapper.createObjectNode()
                .put("role", "user")
                .put("content", userMessage));

        ObjectNode body = mapper.createObjectNode();
        body.put("model", "qwen-flash"); //qwen3-max
        body.set("messages", messages);

        // 发送请求
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // 解析响应
            JsonNode json = mapper.readTree(response.toString());
            if (json.has("choices") && json.get("choices").size() > 0) {
                return json.get("choices").get(0).get("message").get("content").asText();
            } else {
                throw new RuntimeException("API返回格式异常: " + response.toString());
            }
        } else {
            // 处理错误响应
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                errorResponse.append(line);
            }
            throw new RuntimeException("API调用失败 [" + responseCode + "]: " + errorResponse.toString());
        }
    }
}
