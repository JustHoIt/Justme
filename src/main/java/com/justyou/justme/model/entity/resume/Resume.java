package com.justyou.justme.model.entity.resume;


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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String memberName; //작성자 이름 *사용자 정보를 받아오는 방법도 있지만 새로 입력받기
    private String memberEmail; //작성자 이메일
    private String memberPhone; //작성자 휴대폰 번호
    private String resumeTitle; // 이력서 제목
    private String intro; //간단 자기소개


    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educations = new ArrayList<>(); // 1.학력

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Skill> skills = new ArrayList<>(); // 2.기술

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Award> awards = new ArrayList<>(); // 3.수상

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Certification> certifications = new ArrayList<>(); // 4.자격증

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Language> languages = new ArrayList<>(); // 5.외국어 시험 능력 시험

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences = new ArrayList<>(); // 6.업무경력

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios = new ArrayList<>(); // 7.포트폴리오

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<TrainingCourse> trainingCourses = new ArrayList<>(); // 8.교육이수 사항

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Etc> etc = new ArrayList<>(); // 9.기타 사항

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Link> links = new ArrayList<>(); // 10.링크

    public static Resume from(ResumeDto dto) {
        Resume resume = Resume.builder()
                .memberName(dto.getMemberName())
                .memberEmail(dto.getMemberEmail())
                .memberPhone(dto.getMemberPhone())
                .resumeTitle(dto.getResumeTitle())
                .intro(dto.getIntro())
                .build();
        resume.setEducations(dto.getEducations().stream()
                .map(educationDto -> Education.of(educationDto, resume))
                .collect(Collectors.toList()));
        resume.setSkills(dto.getSkills().stream()
                .map(skillDto -> Skill.of(skillDto, resume))
                .collect(Collectors.toList()));
        resume.setAwards(dto.getAwards().stream()
                .map(awardDto -> Award.of(awardDto, resume))
                .collect(Collectors.toList()));
        resume.setCertifications(dto.getCertifications().stream()
                .map(certificationDto -> Certification.of(certificationDto, resume))
                .collect(Collectors.toList()));
        resume.setLanguages(dto.getLanguages().stream()
                .map(languageDto -> Language.of(languageDto, resume))
                .collect(Collectors.toList()));
        resume.setWorkExperiences(dto.getWorkExperiences().stream()
                .map(workExperienceDto -> WorkExperience.of(workExperienceDto, resume))
                .collect(Collectors.toList()));
        resume.setPortfolios(dto.getPortfolios().stream()
                .map(portfolioDto -> Portfolio.of(portfolioDto, resume))
                .collect(Collectors.toList()));
        resume.setTrainingCourses(dto.getTrainingCourses().stream()
                .map(trainingCourseDto -> TrainingCourse.of(trainingCourseDto, resume))
                .collect(Collectors.toList()));
        resume.setEtc(dto.getEtc().stream()
                .map(etcDto -> Etc.of(etcDto, resume))
                .collect(Collectors.toList()));
        resume.setLinks(dto.getLinks().stream()
                .map(linkDto -> Link.of(linkDto, resume))
                .collect(Collectors.toList()));
        return resume;
    }
}
