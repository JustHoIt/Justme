package com.justyou.justme.dto;

import com.justyou.justme.model.entity.MySQL.Member;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalMemberDto {
    private String email;
    private String name;
    private String phone;

    public static WithdrawalMemberDto from(Member member) {
        return WithdrawalMemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }
}
