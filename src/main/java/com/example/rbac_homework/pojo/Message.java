package com.example.rbac_homework.pojo;

import java.sql.Timestamp;

public class Message {
    private int messageId;
    private int userId;
    private String content;
    private Timestamp createTime;

    private boolean isValid;

    public Message() {
        this.isValid=true;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Message(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

}
