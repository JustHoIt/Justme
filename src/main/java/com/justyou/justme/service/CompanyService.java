package com.justyou.justme.service;


import com.justyou.justme.component.MailComponent;
import com.justyou.justme.component.RedisComponent;
import com.justyou.justme.dto.RequestCompanySignUpDto;
import com.justyou.justme.dto.ResponseCompanyDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Redis.RedisCompany;
import com.justyou.justme.model.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisComponent redisComponent;

    private final MailComponent mailComponent;

    private final ModelMapper modelMapper;

    @Transactional
    public ResponseCompanyDto signUp(RequestCompanySignUpDto form) {
        if (this.companyRepository.existsByEmail(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_AUTH_USER);
        }
        if (this.companyRepository.existsByPhone(form.getPhone())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_PHONE);
        }
        String uuid = UUID.randomUUID().toString();
        String encPassword = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
        form.setPassword(encPassword);

        Duration expiration = Duration.ofHours(2);
        RedisCompany redisCompany = RedisCompany.from(form, uuid);
        mailComponent.companySignUpSender(redisCompany, uuid);
        redisComponent.setExpiration(uuid, redisCompany, expiration);

        ResponseCompanyDto responseCompanyDto = modelMapper.map(redisCompany, ResponseCompanyDto.class);
        responseCompanyDto.setResponseStatus("SUCCESS");
        responseCompanyDto.setMessage("회사 HR 회원가입을 완료했습니다.");

        return responseCompanyDto;
    }

}
