package com.example.whatsapp.repository;

import com.example.whatsapp.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
