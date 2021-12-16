package com.mangkyu.employment.interview.app.user.controller;

import com.mangkyu.employment.interview.app.user.dto.AddUserRequest;
import com.mangkyu.employment.interview.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Void> addUser(@RequestBody @Valid final AddUserRequest addUserRequest) {
        userService.addUser(addUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
