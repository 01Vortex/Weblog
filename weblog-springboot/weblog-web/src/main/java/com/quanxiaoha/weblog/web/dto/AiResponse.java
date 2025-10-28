package com.quanxiaoha.weblog.web.dto;

public class AiResponse {
    private String answer;

    public AiResponse(String answer) {
        this.answer = answer;
    }

    // getter/setter
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}