package com.justyou.justme.domains.member.dto;

import com.justyou.justme.domains.member.model.entity.Member;
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
