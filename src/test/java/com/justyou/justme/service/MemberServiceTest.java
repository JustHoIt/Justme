package com.justyou.justme.service;

import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.dto.ResponseDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {


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
        //given
        RequestMemberSignUpDto dto = RequestMemberSignUpDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .password("pw1234")
                .phone("01012345678")
                .birth(LocalDate.parse("1996-05-09"))
                .build();
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .birth(dto.getBirth())
                .phone(dto.getPhone())
                .build();
        // Mock 데이터 설정 (회원 가입 성공 시나리오)
        given(modelMapper.map(any(), any())).willReturn(ResponseDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .message("회원가입이 성공적으로 완료되었습니다.")
                .responseStatus("SUCCESS")
                .build());

        //when
        // 테스트 메소드 호출
        ResponseDto responseDto = modelMapper.map(member, ResponseDto.class);

        //then
        // 예상 결과 확인 및 저장된 데이터 일치하는지 확인
        assertNotNull(responseDto);
        assertEquals("ppp@nana.com", responseDto.getEmail());
        assertEquals("박호민", responseDto.getName());
        assertEquals("회원가입이 성공적으로 완료되었습니다.", responseDto.getMessage());
        assertEquals("SUCCESS", responseDto.getResponseStatus());
    }

    @Test
    @DisplayName("회원 가입 실패 - 중복 이메일")
    public void FAILED_SIGNUP_EXISTS_EMAIL() {
        //given
        given(memberRepository.existsByEmail(any())).willReturn(true);

        //then
        Assertions.assertThatThrownBy(() -> memberService.signUp(RequestMemberSignUpDto.builder()
                        .name("박호민")
                        .email("ppp@test.com")
                        .birth(LocalDate.parse("1996-05-09"))
                        .phone("01012345678")
                        .password("password")
                        .build()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.ALREADY_REGISTERED_EMAIL.getMessage());
    }

    @Test
    @DisplayName("회원 가입 실패 - 중복 휴대폰 번호")
    public void FAILED_SIGNUP_EXISTS_PHONE() {
        //given
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(memberRepository.existsByEmail(any())).willReturn(true);

        //then
        Assertions.assertThatThrownBy(() -> memberService.signUp(RequestMemberSignUpDto.builder()
                        .name("박호민")
                        .email("ppp@test.com")
                        .birth(LocalDate.parse("1996-05-09"))
                        .phone("01012345678")
                        .password("password")
                        .build()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.ALREADY_REGISTERED_EMAIL.getMessage());
    }

}