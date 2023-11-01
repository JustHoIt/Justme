package com.justyou.justme.service;


import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.dto.ResponseDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

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
}
