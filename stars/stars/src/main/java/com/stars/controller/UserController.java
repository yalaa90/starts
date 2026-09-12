package com.stars.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stars.domain.entities.UserEntity;
import com.stars.producers.UserCreationProducer;
import com.stars.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.stars.producers.UserCreationProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor 
@Tag(name = "User", description = "Operations on users")
public class UserController {

    private final UserService userService;
    private final UserCreationProducer userCreationProducer;
    @PostMapping("/save")
    @Operation(summary = "Save user", description = "Buffers a user and saves in batches of 10")
    @ApiResponse(responseCode = "202", description = "User accepted for processing")
    public ResponseEntity<?> saveUsers(@RequestBody UserEntity users) {
        userService.saveUsers(users);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/event-save")
    @Operation(summary = "Save user", description = "Buffers a user and saves in batches of 10")
    @ApiResponse(responseCode = "202", description = "User accepted for processing")
    public ResponseEntity<?> saveUserEvents(@RequestBody UserEntity users) {
        userCreationProducer.sendUserCreationEvent(users);
        return ResponseEntity.accepted().build();
    }

    
}
