package com.example.whatsapp.repository;
import com.example.whatsapp.entity.Group;
import com.example.whatsapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository2 extends JpaRepository<User, Long> {

    List<User> findUsersByGroups(Group group);

    @Query(value = "SELECT u.id, u.telephoneNumber, u.name FROM User u")
    List<User> listUserDto();
}
