package com.justyou.justme.controller;

import com.justyou.justme.dto.RequestCompanySignUpDto;
import com.justyou.justme.security.TokenProvider;
import com.justyou.justme.service.CompanyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
