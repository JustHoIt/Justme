package com.justyou.justme.model.entity.MySQL;


import com.justyou.justme.dto.RequestMemberSignUpDto;
import com.justyou.justme.model.entity.MySQL.resume.Resume;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static com.justyou.justme.UserCode.MemberCode.MEMBER_STATUS_REQ;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MEMBER")
@AuditOverride(forClass = BaseEntity.class)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Email
    private String email; // 이메일
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name; // 이름
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password; //비밀번호
    @NotBlank(message = "휴대폰 번호는 필수 입력 값 입니다.")
    private String phone; //휴대폰번호
    @NotNull(message = "생년월일은 필수 입력 값 입니다.")
    private LocalDate birth;
    private String userStatus; //회원상태
    private String address; //주소
    private String zipCode; //우편번호


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Resume> resumes;

    //이메일 인증 관련 컬럼
    private boolean emailAuth;
    private LocalDateTime emailAuthDate;

    //비밀번호 변경 관련 컬럼
    private String passwordChangeKey;
    private LocalDateTime passwordChangeLimitDt;

    public static Member from(RequestMemberSignUpDto dto) {
        return Member.builder()
                .email(dto.getEmail().toLowerCase(Locale.ROOT))
                .password(dto.getPassword())
                .name(dto.getName())
                .birth(dto.getBirth())
                .phone(dto.getPhone())
                .userStatus(MEMBER_STATUS_REQ.getStatus())
                .emailAuth(false)
                .address(dto.getAddress())
                .zipCode(dto.getZipCode())
                .build();
    }

}
