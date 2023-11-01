package com.justyou.justme.service;


import com.justyou.justme.dto.LogInDto;
import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.dto.ResponseDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto signUp(RequestMemberSignUpDto dto) {

        // 데이터베이스에서 Email && Phone 중복 조회 후 존재하면 예외처리
        if (this.memberRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_EMAIL);
        }
        if (this.memberRepository.existsByPhone(dto.getPhone())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_PHONE);
        }

        //데이터베이스에 비밀번호 암호화해서 저장
//        String uuid = UUID.randomUUID().toString(); 이메일 인증 고민
        String encPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        dto.setPassword(encPassword);

        Member member = memberRepository.save(Member.from(dto));

        //회원가입 성공시 회원의 최소정보 + 메시지로 전달 하기 위해 ResponseDto 로 변환
        ResponseDto responseDto = modelMapper.map(member, ResponseDto.class);
        responseDto.setMessage("회원가입이 성공적으로 완료되었습니다.");
        responseDto.setResponseStatus("SUCCESS");

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
        if (member.getUserStatus().equals("WITHDRAW")) {
            throw new CustomException(ErrorCode.WITHDRAW_USER);
        }

        return member;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) this.memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다." + username));
    }

}
