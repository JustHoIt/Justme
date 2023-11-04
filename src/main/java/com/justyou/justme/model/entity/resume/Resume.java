package com.justyou.justme.model.entity.resume;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.justyou.justme.dto.resume.ResumeDto;
import com.justyou.justme.model.entity.BaseEntity;
import com.justyou.justme.model.entity.Member;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RESUME")
@AuditOverride(forClass = BaseEntity.class)
public class Resume extends BaseEntity {
    //이력서
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String memberName; //작성자 이름 *사용자 정보를 받아오는 방법도 있지만 새로 입력받기
    private String memberEmail; //작성자 이메일
    private String memberPhone; //작성자 휴대폰 번호
    private String resumeTitle; // 이력서 제목
    private String intro; //간단 자기소개


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id")
    private List<Education> educations = new ArrayList<>(); // 1.학력

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_id")
    private List<Skill> skills = new ArrayList<>(); // 2.기술

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "award_id")
    private List<Award> awards = new ArrayList<>(); // 3.수상

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "certifiacate_id")
    private List<Certification> certificates = new ArrayList<>(); // 4.자격증

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private List<Language> languages = new ArrayList<>(); // 5.외국어 시험 능력 시험

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "workexperience_id")
    private List<WorkExperience> workExperiences = new ArrayList<>(); // 6.업무경력

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id")
    private List<Portfolio> portfolios = new ArrayList<>(); // 7.포트폴리오

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "traingcourse_id")
    private List<TrainingCourse> trainingCourses = new ArrayList<>(); // 8.교육이수 사항

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "etc_id")
    private List<Etc> etc = new ArrayList<>(); // 9.기타 사항

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "link_id")
    private List<Link> links = new ArrayList<>(); // 10.링크

    public static Resume from(ResumeDto dto){
        return Resume.builder()
                .memberName(dto.getMemberName())
                .memberEmail(dto.getMemberEmail())
                .memberPhone(dto.getMemberPhone())
                .resumeTitle(dto.getResumeTitle())
                .intro(dto.getIntro())
                .educations(dto.getEducationDtoList().stream()
                        .map(Education::of).collect(Collectors.toList()))
                .skills(dto.getSkillDtoList().stream()
                        .map(Skill::of).collect(Collectors.toList()))
                .awards(dto.getAwardDtoList().stream()
                        .map(Award::of).collect(Collectors.toList()))
                .certificates(dto.getCertificationDtoList().stream()
                        .map(Certification::of).collect(Collectors.toList()))
                .languages(dto.getLanguageDtoList().stream()
                        .map(Language::of).collect(Collectors.toList()))
                .workExperiences(dto.getWorkExperienceDtoList().stream()
                        .map(WorkExperience::of).collect(Collectors.toList()))
                .portfolios(dto.getPortfolioDtoList().stream()
                        .map(Portfolio::of).collect(Collectors.toList()))
                .trainingCourses(dto.getTrainingCourseDtoList().stream()
                        .map(TrainingCourse::of).collect(Collectors.toList()))
                .etc(dto.getEtcDtoList().stream()
                        .map(Etc::of).collect(Collectors.toList()))
                .links(dto.getLinkDtoList().stream()
                        .map(Link::of).collect(Collectors.toList()))
                .build();
    }

}
