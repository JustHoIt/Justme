package com.justyou.justme.controller;

import com.justyou.justme.dto.RequestCompanySignUpDto;
import com.justyou.justme.dto.ResponseCompanyDto;
import com.justyou.justme.security.TokenProvider;
import com.justyou.justme.service.CompanyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "유저 - 회원가입")
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestCompanySignUpDto form){
        return ResponseEntity.ok(this.companyService.signUp(form));
    }

    @ApiOperation(value = "이메일 인증")
    @GetMapping("/emailAuth")
    public ResponseEntity<ResponseCompanyDto> emailAuth(@RequestParam String id){
        return ResponseEntity.ok(this.companyService.emailAuth(id));
    }
}
