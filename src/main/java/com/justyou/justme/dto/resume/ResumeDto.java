package com.justyou.justme.dto.resume;


import com.justyou.justme.model.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResumeDto {
    private Member member;
    private String memberName; //작성자 이름
    private String memberEmail; //작성자 이메일
    private String memberPhone; //작성자 휴대폰 번호
    private String resumeTitle; // 이력서 제목
    private String intro; // 간단 자기소개
    private List<EducationDto> educationDtoList;
    private List<SkillDto> skillDtoList;
    private List<AwardDto> awardDtoList;
    private List<CertificationDto> certificationDtoList;
    private List<LanguageDto> languageDtoList;
    private List<WorkExperienceDto> workExperienceDtoList;
    private List<PortfolioDto> portfolioDtoList;
    private List<TrainingCourseDto> trainingCourseDtoList;
    private List<EtcDto> etcDtoList;
    private List<LinkDto> linkDtoList;

}
