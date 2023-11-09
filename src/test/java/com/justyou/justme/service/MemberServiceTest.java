package com.justyou.justme.service;

import com.justyou.justme.component.MailComponent;
import com.justyou.justme.dto.MemberDto;
import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.dto.ResponseUserDto;
import com.justyou.justme.dto.UserDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.MySQL.Member;
import com.justyou.justme.model.repository.MemberRepository;
import com.justyou.justme.model.repository.WithdrawalMemberRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static com.justyou.justme.UserCode.MemberCode.MEMBER_STATUS_ING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {


    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private WithdrawalMemberRepository withdrawalMemberRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MailComponent mailComponent;

    @AfterEach
    public void afterEach() {

    }

    @Test
    @DisplayName("회원 가입 - 성공")
    @Transactional
    void success_signup() {
        //given
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(modelMapper.map(any(), any())).willReturn(ResponseUserDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .message("회원가입을 성공했습니다.")
                .responseStatus("SUCCESS")
                .build());

        //when
        // 테스트 메소드 호출
        ResponseUserDto responseUserDto = memberService.signUp(RequestMemberSignUpDto.builder()
                .email("ppp@nana.com")
                .password("password")
                .birth(LocalDate.parse("1996-05-09"))
                .phone("01012345678")
                .name("박호민")
                .build());

        //then
        assertNotNull(responseUserDto);
        assertEquals("ppp@nana.com", responseUserDto.getEmail());
        assertEquals("박호민", responseUserDto.getName());
        assertEquals("회원가입을 성공했습니다.", responseUserDto.getMessage());
        assertEquals("SUCCESS", responseUserDto.getResponseStatus());
    }

    @Test
    @DisplayName("회원 가입 - 실패(중복 이메일)")
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
    @DisplayName("회원 가입 - 실패(중복 휴대폰 번호)")
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

    @Test
    @DisplayName("회원 정보 조회 - 성공")
    public void SUCCESS_GET_USER_INFO() {
        //given
        Member member = Member.builder()
                .id(1L)
                .name("박호민")
                .email("ppp@test.com")
                .birth(LocalDate.parse("1996-05-09"))
                .phone("01012345678")
                .password("password")
                .emailAuth(true)
                .emailAuthDate(LocalDateTime.now())
                .userStatus(MEMBER_STATUS_ING.getStatus())
                .build();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(modelMapper.map(any(), any())).willReturn(MemberDto.builder()
                .id(1L)
                .name("박호민")
                .email("ppp@test.com")
                .birth(LocalDate.parse("1996-05-09"))
                .phone("01012345678")
                .address("서울시 강남구 @@로 @@길")
                .zipCode("01765")
                .build());
        //when
        MemberDto result = memberService.getUserInfo(UserDto.builder()
                .id(1L)
                .email("ppp@test.com")
                .build());
        //then
        assertNotNull(result);
        assertEquals("박호민", result.getName());
        assertEquals("ppp@test.com", result.getEmail());
        assertEquals("01012345678", result.getPhone());
    }

    @Test
    @DisplayName("회원 정보 조회 - 실패")
    public void FAIL_GET_USER_INFO() {
        //given
        given(memberRepository.findById(any())).willReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy(() -> memberService.getUserInfo(UserDto.builder()
                        .id(1L)
                        .email("ppp@test.com")
                        .build()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());

    }

    @Test
    @DisplayName(value = "회원탈퇴 - 성공")
    public void SUCCESS_WITHDRAWAL() {
        //given
        Member member = Member.builder()
                .id(1L)
                .name("박호민")
                .email("ppp@test.com")
                .birth(LocalDate.parse("1996-05-09"))
                .phone("01012345678")
                .password("password")
                .emailAuth(true)
                .emailAuthDate(LocalDateTime.now())
                .userStatus(MEMBER_STATUS_ING.getStatus())
                .build();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(modelMapper.map(any(), any())).willReturn(ResponseUserDto.builder()
                .email("ppp@test.com")
                .name("박호민")
                .message("회원탈퇴가 성공적으로 이루어졌습니다.")
                .responseStatus("SUCCESS")
                .build());
        //when
        ResponseUserDto responseUserDto = memberService.deleteUser(UserDto.builder()
                .id(1L)
                .email("ppp@test.com")
                .build());
        //then
        assertNotNull(responseUserDto);
    }

    @Test
    @DisplayName("회원탈퇴 - 실패")
    public void FAIL_WITHDRAWAL() {
        //given
        given(memberRepository.findById(any())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> memberService.deleteUser(UserDto.builder()
                        .id(1L)
                        .email("ppp@test.com")
                        .build()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
    }
}