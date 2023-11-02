package com.justyou.justme.service;


import com.justyou.justme.component.MailComponent;
import com.justyou.justme.dto.*;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.entity.WithdrawalMember;
import com.justyou.justme.model.repository.MemberRepository;
import com.justyou.justme.model.repository.WithdrawalMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.justyou.justme.UserCode.MemberCode.MEMBER_STATUS_ING;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final WithdrawalMemberRepository withdrawalMemberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final MailComponent mailComponent;

    @Transactional
    public ResponseDto signUp(RequestMemberSignUpDto dto) {
        long startTime = System.currentTimeMillis();
        // 데이터베이스에서 Email && Phone 중복 조회 후 존재하면 예외처리
        if (this.memberRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_EMAIL);
        }
        if (this.memberRepository.existsByPhone(dto.getPhone())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_PHONE);
        }
        //데이터베이스에 비밀번호 암호화해서 저장
        String uuid = UUID.randomUUID().toString();
        String encPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        dto.setPassword(encPassword);

        Member member = Member.from(dto);
        member.setEmailAuthKey(uuid);
        member = memberRepository.save(member);

        boolean result = mailComponent.signUpSender(member);

        //회원가입 성공시 회원의 최소정보 + 메시지로 전달 하기 위해 ResponseDto 로 변환
        ResponseDto responseDto = modelMapper.map(member, ResponseDto.class);
        responseDto.setMessage("회원가입이 성공적으로 완료되었습니다.");
        responseDto.setResponseStatus("SUCCESS");
        long stopTime = System.currentTimeMillis();

        log.info("Service 동작 걸린시간 : " + (stopTime - startTime) + " | 이메일 발송 결과 : " + result);

        return responseDto;
    }

    //휴대폰 번호 중복 체크
    public boolean checkPhone(String phone) {
        return this.memberRepository.existsByPhone(phone);
    }

    //이메일 중복 체크
    public boolean checkEmail(String email) {
        return this.memberRepository.existsByEmail(email);
    }

    //이메일 인증
    public ResponseDto emailAuth(String uuid) {
        Optional<Member> optionalMember = this.memberRepository.findByEmailAuthKey(uuid);
        if (optionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Member member = optionalMember.get();
        if (member.isEmailAuth()) {
            throw new CustomException(ErrorCode.ALREADY_AUTH_USER);
        }

        member.setEmailAuth(true);
        member.setEmailAuthDate(LocalDateTime.now());
        member.setUserStatus(MEMBER_STATUS_ING.getStatus());
        memberRepository.save(member);

        ResponseDto responseDto = modelMapper.map(member, ResponseDto.class);
        responseDto.setMessage("이메일 인증이 완료되었습니다.");
        responseDto.setResponseStatus("SUCCESS");

        return responseDto;
    }

    //유저 로그인 인증
    public Member authenticate(LogInDto form) {
        /*1. 유저 아이디 검색 후 일치하는 아이디 확인한뒤 없으면 NOT_MATCH_USER EXCEPTION 처리
         * 2. 입력한 비밀번호와 회원 비밀번호를 비교하여 일치 하지않으면  NOT_MATCH_USER EXCEPTION 처리(비밀번호가 틀렸다고 하면 다른사람이 시도할 수 있음)
         * 3. 유저의 상태를 보고 인증전, 회원탈퇴 유저는 EXCEPTION 처리 */
        Optional<Member> optionalMember = memberRepository.findByEmail(form.getEmail());
        if (optionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_MATCH_USER);
        }
        Member member = optionalMember.get();
        if (!this.passwordEncoder.matches(form.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_USER);
        }
        if (member.getUserStatus().equals("REQ")) {
            throw new CustomException(ErrorCode.NOT_EMAIL_AUTHENTICATE_USER);
        }
        return member;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) this.memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다." + username));
    }

    //회원정보 찾기
    public boolean findUser(RequestFindUserDto form) {
        Optional<Member> optionalMember = memberRepository.findByEmailAndName(form.getEmail(), form.getName());
        long startTime = System.currentTimeMillis();
        if (optionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Member member = optionalMember.get();

        //비밀번호 변경키와 제한시간 현재시간으로부터 1시간까지만 유효 하도록 생성
        String uuid = UUID.randomUUID().toString();
        member.setPasswordChangeKey(uuid);
        member.setPasswordChangeLimitDt(LocalDateTime.now().plusHours(1));

        memberRepository.save(member);
        boolean result = mailComponent.changePasswordAuthSender(member);
        //조회성공시  true 전달하기
        long stopTime = System.currentTimeMillis();
        log.info("Service 동작 걸린시간 : " + (stopTime - startTime) + " | 이메일 발송 결과 : " + result);
        return true;
    }

    //비밀번호 변경
    public ResponseDto changePassword(RequestNewPasswordDto form) {
        Optional<Member> optionalMember = memberRepository.findByPasswordChangeKey(form.getUUID());
        //유저 정보 없으면 예외처리
        if (optionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Member member = optionalMember.get();
        // 변경 기간이 지났거나 존재하지 않을때 예외처리
        if (member.getPasswordChangeLimitDt() == null) {
            throw new CustomException(ErrorCode.NOT_VALID_DATE);
        }
        if (member.getPasswordChangeLimitDt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.TIME_OUT);
        }

        String pw = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
        member.setPassword(pw);
        member.setPasswordChangeKey("");
        member.setPasswordChangeLimitDt(null);
        memberRepository.save(member);

        //최소정보 Dto 담아서 보내기
        ResponseDto responseDto = modelMapper.map(member, ResponseDto.class);
        responseDto.setMessage("비밀번호 변경이 완료되었습니다.");
        responseDto.setResponseStatus("SUCCESS");

        return responseDto;
    }

    public MemberDto getUserInfo(UserDto user) {
        Member member = memberRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return modelMapper.map(member, MemberDto.class);
    }

    public ResponseDto deleteUser(UserDto user) {
        Optional<Member> optionalMember = memberRepository.findById(user.getId());
        if (optionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        /*회원탈퇴 시나리오
         * 회원탈퇴를 하더라도 최소의 정보는 남겨두기 위해서 탈퇴회원 Entity 를 만들어서 정보를 이관 후 관리
         * 나머지 정보는 전부 삭제*/
        Member member = optionalMember.get();
        WithdrawalMemberDto withdrawalMemberDto = WithdrawalMemberDto.from(member);
        WithdrawalMember withdrawalMember = WithdrawalMember.from(withdrawalMemberDto);
        withdrawalMember = withdrawalMemberRepository.save(withdrawalMember);

        memberRepository.deleteById(member.getId());

        ResponseDto responseDto = modelMapper.map(withdrawalMember, ResponseDto.class);
        responseDto.setMessage("회원탈퇴가 성공적으로 이루어졌습니다.");
        responseDto.setResponseStatus("SUCCESS");

        return responseDto;
    }
}
