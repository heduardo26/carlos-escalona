package com.example.whatsapp.service.implement;

import com.example.whatsapp.dto.MessageDto;
import com.example.whatsapp.entity.Group;
import com.example.whatsapp.entity.Message;
import com.example.whatsapp.entity.User;
import com.example.whatsapp.exceptions.BadArgumentsException;
import com.example.whatsapp.exceptions.ResourceNotFoundException;
import com.example.whatsapp.general.MessageType;
import com.example.whatsapp.general.UtilsDate;
import com.example.whatsapp.repository.GroupRepository;
import com.example.whatsapp.repository.MessageRepository;
import com.example.whatsapp.repository.UserRepository;
import com.example.whatsapp.service.MessageService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.type.TypeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final GroupRepository groupRepo;

    @Autowired
    MessageServiceImpl(MessageRepository messageRepo, UserRepository userRepo, GroupRepository groupRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    /**
     * Funtion to send a message to a Group
     *
     * @param messageDto
     * @throws RuntimeException
     * @throws IllegalArgumentException
     */
    public void sendMessage(MessageDto messageDto) {

        //Validate null fields
        if (checkNullFields(messageDto)) {
            throw new BadArgumentsException("There are null fields");
        }
        //Validate that sender exist
        User user = validateUserId(messageDto.getSenderId());
        //validate that group exist
        Group group = validateGroupId(messageDto.getGroupId());

        /** Save the message into the BD */
        Message newMessage = Message.builder()
                .date((UtilsDate.getCurrentDateAndTime()))
                .type(MessageType.valueOf(messageDto.getType()))
                .sender(user)
                .receiver(group)
                .text(messageDto.getText())
                .build();
        messageRepo.save(newMessage);


        /** Notify to all users of the group */
        //Get the users of the group
        List<User> users = userRepo.findUsersByGroups(group);
        //Remove the sender as it doesn't need to be notified
        users.remove(user);
        users.forEach(u -> notifyNewMassage(u.getTelephoneNumber(), newMessage.getText()));

    }

    /**
     * This function get all the messages from a group
     *
     * @param groupId
     * @return
     * @throws RuntimeException
     */
    public List<Message> getMessagesByGroup(Long groupId) throws RuntimeException {
        Group group = validateGroupId(groupId);
        return messageRepo.getMessageByReceiver(group);
    }


    /**
     * This function validates if a Group is valid
     *
     * @param groupId
     * @return
     * @throws RuntimeException
     */
    private Group validateGroupId(Long groupId) {
        return groupRepo.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }

    /**
     * This function validates if a User is valid
     *
     * @param userId
     * @return
     * @throws RuntimeException
     */
    private User validateUserId(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
    }

    /**
     * This procedure simulates a notification, that notification can be sent to a queue or stored in a database or something like that.
     *
     * @param phoneNumber
     */
    private void notifyNewMassage(String phoneNumber, String text) {
        log.info("Sending notification to:{} with the message: {}", phoneNumber, text);
    }

    /**
     * This function validates that no null fields exist
     *
     * @param messageDto
     * @return
     */
    private boolean checkNullFields(MessageDto messageDto) {
        if(Objects.isNull(messageDto.getGroupId())){
            return true;
        }

        if(Objects.isNull(messageDto.getSenderId())){
            return true;
        }

        if(Objects.isNull(messageDto.getText())){
            return true;
        }

        return false;
    }
}
