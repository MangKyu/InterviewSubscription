package com.mangkyu.employment.interview.app.member.controller;

import com.mangkyu.employment.interview.app.member.dto.AddMemberRequest;
import com.mangkyu.employment.interview.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/user")
    public ResponseEntity<Void> addUser(@RequestBody @Valid final AddMemberRequest addMemberRequest) {
        memberService.addUser(addMemberRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
