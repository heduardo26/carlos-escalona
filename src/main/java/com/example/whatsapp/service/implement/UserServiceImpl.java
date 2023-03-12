package com.example.whatsapp.service.implement;

import com.example.whatsapp.dto.UserDto;
import com.example.whatsapp.entity.User;
import com.example.whatsapp.repository.UserRepository;
import com.example.whatsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.getUsersDto();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * This function converts a User entity class into a UserDto class
     * @param user
     * @return
     */
    private UserDto convertToDto(User user){
        UserDto userDto = new UserDto(user.getId(), user.getTelephoneNumber(), user.getName());
        return userDto;
    }
}
