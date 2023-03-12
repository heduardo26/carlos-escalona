package com.example.whatsapp.service;

import com.example.whatsapp.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
}
