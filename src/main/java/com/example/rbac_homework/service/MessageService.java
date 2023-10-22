package com.example.rbac_homework.service;

import com.example.rbac_homework.pojo.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();
    void sendMessage(Message message);

    void deleteMessage(int messageId);
}
