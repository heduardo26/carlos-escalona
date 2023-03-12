package com.example.whatsapp.repository;

import com.example.whatsapp.dto.MessageDto;
import com.example.whatsapp.entity.Group;
import com.example.whatsapp.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> getMessageByReceiver(Group group);
}
