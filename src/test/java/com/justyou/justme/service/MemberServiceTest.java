package com.justyou.justme.service;

import com.justyou.justme.dto.MemberDto;
import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.dto.ResponseDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ModelMapper modelMapper;

    @AfterEach
    public void afterEach() {

    }

    @Test
    @DisplayName("회원 가입 성공")
    @Transactional
    void success_signup() {
        //가상 데이터 생성
        RequestMemberSignUpDto dto = RequestMemberSignUpDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .password("pw1234")
                .phone("01012345678")
                .birth(LocalDate.parse("1996-05-09"))
                .build();

        // Mock 데이터 설정 (회원 가입 성공 시나리오)
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(memberRepository.existsByPhone(any())).willReturn(false);

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .birth(dto.getBirth())
                .phone(dto.getPhone())
                .build();

        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(modelMapper.map(eq(member), eq(ResponseDto.class))).thenReturn(ResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .responseStatus("SUCCESS")
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build());

        // 테스트 메소드 호출
        ResponseDto responseDto = memberService.signUp(dto);

        // 예상 결과 확인 및 저장된 데이터 일치하는지 확인
        assertNotNull(responseDto);
        assertEquals("ppp@nana.com", responseDto.getEmail());
        assertEquals("박호민", responseDto.getName());
        assertEquals("회원가입이 성공적으로 완료되었습니다.", responseDto.getMessage());
        assertEquals("SUCCESS", responseDto.getResponseStatus());
    }

    @Test
    @DisplayName("회원 가입 실패 - 중복 이메일")
    @Transactional
    public void FAILED_SIGNUP_EXISTS_EMAIL(){
        //가상 데이터 생성
        RequestMemberSignUpDto dto = RequestMemberSignUpDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .password("pw1234")
                .phone("01012345678")
                .birth(LocalDate.parse("1996-05-09"))
                .build();

        // Mock 데이터 설정 (이미 등록된 이메일로 가입 시나리오)
        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        //테스트 호출
        try {
            memberService.signUp(dto);
            fail("예외처리 발생하지 않음");
        }catch (CustomException e){
            assertEquals(ErrorCode.ALREADY_REGISTERED_EMAIL, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("회워 가입 실패 - 중복 휴대폰 번호")
    @Transactional
    public void FAILED_SIGNUP_EXISTS_PHONE(){
        //가상 데이터 생성
        RequestMemberSignUpDto dto = RequestMemberSignUpDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .password("pw1234")
                .phone("01012345678")
                .birth(LocalDate.parse("1996-05-09"))
                .build();

        // Mock 데이터 설정 (이미 등록된 휴대폰 번호로 가입 시나리오)
        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(memberRepository.existsByPhone(dto.getPhone())).thenReturn(true);

        //테스트 호출
        try {
            memberService.signUp(dto);
            fail("예외처리 발생하지 않음");
        }catch (CustomException e){
            assertEquals(ErrorCode.ALREADY_REGISTERED_PHONE, e.getErrorCode());
        }
    }
}