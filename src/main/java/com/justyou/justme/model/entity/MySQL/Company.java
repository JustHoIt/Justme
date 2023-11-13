package com.justyou.justme.model.entity.MySQL;


import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

import static com.justyou.justme.UserCode.CompanyCode.COMPANY_STATUS_REQ;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "COMPANY")
@AuditOverride(forClass = BaseEntity.class)
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    private String companyName; //회사명
    private String email; //회사 HR 이메일
    private String password; //비밀번호
    private String officeTel; //회사 번호
    private String phone; // 인사 담당자 번호
    private String companyStatus; //회사 상태
    private boolean companyHRMAuth; //인적 자원 관리자 인증 여부;
    private LocalDateTime companyHRMAuthDate;

//    public static Company from(){
//        return Company.builder()
//                .companyName()
//                .email()
//                .password()
//                .companyHRMAuth(false)
//                .companyStatus(COMPANY_STATUS_REQ.getStatus())
//                .build();
}

