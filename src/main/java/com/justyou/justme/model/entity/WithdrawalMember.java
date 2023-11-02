package com.justyou.justme.model.entity;


import com.justyou.justme.dto.WithdrawalMemberDto;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity(name = "withdrawal_member_id")
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class WithdrawalMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_member_id")
    private Long id;
    private String email; // 이메일
    private String name; // 이름
    private String phone; //휴대폰번호
    private LocalDateTime withdrawalDt; //탈퇴한 날짜

    public static WithdrawalMember from(WithdrawalMemberDto dto) {
        return WithdrawalMember.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .name(dto.getName())
                .withdrawalDt(LocalDateTime.now())
                .build();
    }
}
