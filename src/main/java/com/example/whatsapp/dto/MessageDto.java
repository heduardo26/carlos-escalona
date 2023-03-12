package com.example.whatsapp.dto;

import com.example.whatsapp.general.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDto {
    private String type;
    private String text;
    private Long senderId;
    private Long groupId;
}
