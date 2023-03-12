package com.example.whatsapp.controller;

import com.example.whatsapp.dto.GroupDto;
import com.example.whatsapp.dto.MessageDto;
import com.example.whatsapp.dto.UserDto;
import com.example.whatsapp.entity.Group;
import com.example.whatsapp.entity.Message;
import com.example.whatsapp.entity.User;
import com.example.whatsapp.exceptions.BadArgumentsException;
import com.example.whatsapp.exceptions.ResourceNotFoundException;
import com.example.whatsapp.general.MessageType;
import com.example.whatsapp.service.MessageService;
import com.example.whatsapp.service.UserService;
import com.example.whatsapp.service.implement.GroupServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GroupServiceImpl mockGroupService;
    @MockBean
    private MessageService mockMessageService;
    @MockBean
    private UserService mockUserService;

    @Test
    void testGetUsers() throws Exception {
        // Setup
        // Configure UserService.getUsers(...).
        final UserDto userDto = new UserDto();
        userDto.setId(0L);
        userDto.setPhoneNumber("phoneNumber");
        userDto.setName("name");
        final List<UserDto> users = List.of(userDto);
        when(mockUserService.getUsers()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/whatsapp/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }



    @Test
    void testGetGroups() throws Exception {
        // Setup
        // Configure GroupServiceImpl.getGroups(...).
        final List<GroupDto> groups = List.of(new GroupDto(1L, "Xcale"));
        when(mockGroupService.getGroups()).thenReturn(groups);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/whatsapp/groups")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }


    @Test
    void testSendMessage() throws Exception {
        // Setup
        MessageDto message = new MessageDto("TEXT","El mensaje", 1L, 1L);

        ResultActions response = mockMvc.perform(post("/api/v1/whatsapp/send-message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk());

    }

    @Disabled("TODO: Still need to work on it")
    @Test
    void testSendMessage_MessageServiceThrowsResourceNotFoundException() throws Exception {
        // Setup
        /*doThrow(ResourceNotFoundException.class).when(mockMessageService).sendMessage(new MessageDto("TEXT","El mensaje", 1L, 19L));
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/whatsapp/send-message")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        */
        // Setup
        doThrow(ResourceNotFoundException.class).when(mockMessageService).sendMessage(new MessageDto());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/whatsapp/send-message")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());


        /* // Setup
        MessageDto message = new MessageDto("TEXT","El mensaje", 1L, 18955L);

        ResultActions response = mockMvc.perform(post("/api/v1/whatsapp/send-message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isNotFound());*/
    }

    @Test
    void testSendMessage_MessageServiceBadArgumentsException() throws Exception {
        // Setup
        doThrow(BadArgumentsException.class).when(mockMessageService).sendMessage(new MessageDto());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/whatsapp/send-message")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testGetMessagesByGroup() throws Exception {
        // Setup
        // Configure MessageService.getMessagesByGroup(...).
        final List<Message> messages = List.of(new Message());
        when(mockMessageService.getMessagesByGroup(1L)).thenReturn(messages);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/whatsapp/group/{id}/messages", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //assertThat(response.getContentAsString()).isEqualTo("message send!");
    }

}
