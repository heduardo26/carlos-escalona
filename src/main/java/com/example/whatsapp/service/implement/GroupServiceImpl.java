package com.example.whatsapp.service.implement;

import com.example.whatsapp.dto.GroupDto;
import com.example.whatsapp.dto.UserDto;
import com.example.whatsapp.entity.Group;
import com.example.whatsapp.entity.User;
import com.example.whatsapp.repository.GroupRepository;
import com.example.whatsapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupDto> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    /**
     * This function converts a Group entity class into a GroupDto class
     * @param group
     * @return
     */
    private GroupDto convertToDto(Group group){
        GroupDto groupDto = new GroupDto(group.getId(), group.getName());
        return groupDto;
    }

}
