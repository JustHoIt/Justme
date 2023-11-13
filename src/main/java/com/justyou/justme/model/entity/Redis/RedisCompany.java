package com.justyou.justme.model.entity.Redis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.justyou.justme.dto.RequestCompanySignUpDto;
import com.justyou.justme.dto.RequestMemberSignUpDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;


@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("user")
public class RedisCompany implements Serializable {
    @Id
    private String id;
    private String companyName; //회사명
    private String email; //회사 HR 이메일
    private String password; //비밀번호
    private String officeTel; //회사 번호
    private String phone; // 인사 담당자 번호


    public static RedisCompany from(RequestCompanySignUpDto dto, String uuid) {
        return RedisCompany.builder()
                .id(uuid)
                .email(dto.getEmail().toLowerCase(Locale.ROOT))
                .password(dto.getPassword())
                .companyName(dto.getCompanyName())
                .officeTel(dto.getOfficeTel())
                .phone(dto.getPhone())
                .build();
    }
}
