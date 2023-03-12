package com.example.whatsapp.repository;

import com.example.whatsapp.entity.Group;
import com.example.whatsapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByGroups(Group id);

    @Query(value = "SELECT new User(u.id,u.telephoneNumber,u.name) FROM User u")
    List<User> getUsersDto();
}
