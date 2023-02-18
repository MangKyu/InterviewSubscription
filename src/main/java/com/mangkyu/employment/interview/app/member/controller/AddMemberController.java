package com.mangkyu.employment.interview.app.member.controller;

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
class AddMemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Void> add(@RequestBody @Valid final AddMemberRequest request) {
        memberService.addUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
