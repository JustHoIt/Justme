package com.justyou.justme.dto.resume;


import com.justyou.justme.model.entity.MySQL.resume.Resume;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {
    private Long memberId;
    private String memberName; //작성자 이름
    private String memberEmail; //작성자 이메일
    private String memberPhone; //작성자 휴대폰 번호
    private String resumeTitle; // 이력서 제목
    private String intro; // 간단 자기소개
    private List<EducationDto> educations;
    private List<SkillDto> skills;
    private List<AwardDto> awards;
    private List<CertificationDto> certifications;
    private List<LanguageDto> languages;
    private List<WorkExperienceDto> workExperiences;
    private List<PortfolioDto> portfolios;
    private List<TrainingCourseDto> trainingCourses;
    private List<EtcDto> etc;
    private List<LinkDto> links;

    public static ResumeDto from(Resume resume){
        ResumeDto resumeDto = ResumeDto.builder()
                .memberName(resume.getMemberName())
                .memberEmail(resume.getMemberEmail())
                .memberPhone(resume.getMemberPhone())
                .resumeTitle(resume.getResumeTitle())
                .intro(resume.getIntro())
                .build();
        resumeDto.setEducations(resume.getEducations()
                .stream().map(education -> EducationDto.of(education))
                .collect(Collectors.toList()));
        resumeDto.setSkills(resume.getSkills()
                .stream().map(skill -> SkillDto.of(skill))
                .collect(Collectors.toList()));
        resumeDto.setAwards(resume.getAwards()
                .stream().map(award -> AwardDto.of(award))
                .collect(Collectors.toList()));
        resumeDto.setCertifications(resume.getCertifications()
                .stream().map(certification -> CertificationDto.of(certification))
                .collect(Collectors.toList()));
        resumeDto.setLanguages(resume.getLanguages()
                .stream().map(language -> LanguageDto.of(language))
                .collect(Collectors.toList()));
        resumeDto.setWorkExperiences(resume.getWorkExperiences()
                .stream().map(workExperience -> WorkExperienceDto.of(workExperience))
                .collect(Collectors.toList()));
        resumeDto.setEtc(resume.getEtc()
                .stream().map(etc -> EtcDto.of(etc))
                .collect(Collectors.toList()));
        resumeDto.setLinks(resume.getLinks()
                .stream().map(link -> LinkDto.of(link))
                .collect(Collectors.toList()));
        resumeDto.setPortfolios(resume.getPortfolios()
                .stream().map(portfolio -> PortfolioDto.of(portfolio))
                .collect(Collectors.toList()));
        resumeDto.setTrainingCourses(resume.getTrainingCourses()
                .stream().map(trainingCourse -> TrainingCourseDto.of(trainingCourse))
                .collect(Collectors.toList()));
        return  resumeDto;
    }
}
