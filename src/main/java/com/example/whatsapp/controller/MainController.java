package com.example.whatsapp.controller;

import com.example.whatsapp.dto.MessageDto;
import com.example.whatsapp.exceptions.BadArgumentsException;
import com.example.whatsapp.exceptions.ResourceNotFoundException;
import com.example.whatsapp.service.implement.GroupServiceImpl;
import com.example.whatsapp.service.MessageService;
import com.example.whatsapp.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/whatsapp")
@Api(value = "Main controller")
public class MainController {

    private final GroupServiceImpl groupService;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MainController(GroupServiceImpl groupService, MessageService messageService, UserService userService) {
        this.groupService = groupService;
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all the Users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal errors. This means that some invariants expected by the underlying system have been broken. This error code is reserved for serious errors.")
    })
    public ResponseEntity<Object> getUsers(){

        try {
            return new ResponseEntity(userService.getUsers(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/groups")
    @ApiOperation(value = "Get all the groups")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal errors. This means that some invariants expected by the underlying system have been broken. This error code is reserved for serious errors.")
    })
    public ResponseEntity<Object> getGroups(){
        return new ResponseEntity(groupService.getGroups(), HttpStatus.OK);
    }


    @PostMapping("/send-message")
    @ApiOperation(value = "Send a message to a group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "Bad Request/Invalid Arguments"),
            @ApiResponse(code = 404, message = "Not found (Resource not found, invalid url)"),
            @ApiResponse(code = 500, message = "Internal errors. This means that some invariants expected by the underlying system have been broken. This error code is reserved for serious errors.")
    })
    public ResponseEntity<Object> sendMessage(@RequestBody MessageDto messageDto) throws IllegalArgumentException {
        try{
            messageService.sendMessage(messageDto);
            return new ResponseEntity("message send!", HttpStatus.OK);
        }catch (ResourceNotFoundException r){
            return new ResponseEntity(r.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BadArgumentsException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/group/{id}/messages")
    @ApiOperation(value = "Get all messages of a group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal errors. This means that some invariants expected by the underlying system have been broken. This error code is reserved for serious errors.")
    })
    public ResponseEntity<Object> getMessagesByGroup(@PathVariable @ApiParam(name = "id", value = "Group id", example = "1") Long id) throws RuntimeException{
        try {
            return new ResponseEntity(messageService.getMessagesByGroup(id), HttpStatus.OK);
        }catch (ResourceNotFoundException r){
            return new ResponseEntity(r.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


}
