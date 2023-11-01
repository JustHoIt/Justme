package com.justyou.justme.controller;


import com.justyou.justme.dto.LogInDto;
import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.dto.ResponseDto;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.security.TokenProvider;
import com.justyou.justme.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    /*@Valid*/
    @ApiOperation(value = "회원가입")
    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody RequestMemberSignUpDto memberSignUpRequest) {
        log.info(memberSignUpRequest.getEmail());
        return ResponseEntity.ok(this.memberService.signUp(memberSignUpRequest));
    }

    @ApiOperation(value = "휴대폰 중복 체크")
    @GetMapping("/check/phone")
    public ResponseEntity<Boolean> checkPhone(@RequestParam String phone) {
        return ResponseEntity.ok(this.memberService.checkPhone(phone));
    }

    @ApiOperation(value = "이메일 중복 체크")
    @GetMapping("/check/email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(this.memberService.checkEmail(email));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LogInDto form) {
        Member member = this.memberService.authenticate(form);
        String token = this.tokenProvider.generateToken(member.getEmail(), member.getId());
        return ResponseEntity.ok(token);
    }
}
