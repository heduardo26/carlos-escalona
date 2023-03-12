package com.example.whatsapp.service;

import com.example.whatsapp.dto.MessageDto;
import com.example.whatsapp.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(MessageDto message) ;

    List<Message> getMessagesByGroup(Long GroupId);
}
