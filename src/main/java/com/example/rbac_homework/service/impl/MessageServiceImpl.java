package com.example.rbac_homework.service.impl;

import com.example.rbac_homework.dao.MessageMapper;
import com.example.rbac_homework.pojo.Message;
import com.example.rbac_homework.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<Message> getAllMessages() {
        return messageMapper.showAllMessages();
    }

    @Override
    public void sendMessage(Message message) {
        messageMapper.sendMessage(message);
    }

    @Override
    public void deleteMessage(int messageId) {
        messageMapper.deleteMessage(messageId);
    }
}
