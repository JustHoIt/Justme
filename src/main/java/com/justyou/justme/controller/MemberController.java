package com.justyou.justme.controller;


import com.justyou.justme.dto.*;
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
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody RequestMemberSignUpDto form) {
        return ResponseEntity.ok(this.memberService.signUp(form));
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

    @ApiOperation(value = "이메일 인증")
    @GetMapping("email-auth")
    public ResponseEntity<ResponseDto> emailAuth(@RequestParam String id) {
        return ResponseEntity.ok(this.memberService.emailAuth(id));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LogInDto form) {
        Member member = this.memberService.authenticate(form);
        String token = this.tokenProvider.generateToken(member.getEmail(), member.getId());
        return ResponseEntity.ok(token);
    }

    @ApiOperation(value = "비밀번호 변경 - 유저 정보 찾기")
    @PostMapping("/password/findUser")
    public ResponseEntity<Boolean> findUser(@Valid @RequestBody RequestFindUserDto form) {
        return ResponseEntity.ok(this.memberService.findUser(form));
    }

    @ApiOperation(value = "비밀번호 변경 - 새 비밀번호 입력")
    @PostMapping("/password/new")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody RequestNewPasswordDto form) {
        return ResponseEntity.ok(this.memberService.changePassword(form));
    }

    @ApiOperation(value = "회원정보 조회")
    @GetMapping("/info")
    public ResponseEntity<MemberDto> getUserInfo(@RequestHeader(name = "auth-token") String token) {
        return ResponseEntity.ok(memberService.getUserInfo(tokenProvider.getUser(token)));
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> deleteUser(@RequestHeader(name = "auth-token") String token) {
        return ResponseEntity.ok(memberService.deleteUser(tokenProvider.getUser(token)));
    }

}
