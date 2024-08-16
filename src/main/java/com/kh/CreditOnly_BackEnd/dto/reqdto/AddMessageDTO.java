package com.kh.CreditOnly_BackEnd.dto.reqdto;

public class AddMessageDTO {
    private Long conversationId;
    private String sender;
    private String content;

    // Getters and setters
    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
