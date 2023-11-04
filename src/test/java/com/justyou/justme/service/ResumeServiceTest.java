package com.justyou.justme.service;

import com.justyou.justme.dto.ResponseResumeDto;
import com.justyou.justme.dto.resume.*;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.entity.resume.Link;
import com.justyou.justme.model.repository.MemberRepository;
import com.justyou.justme.model.repository.ResumeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.justyou.justme.UserCode.MemberCode.MEMBER_STATUS_ING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ResumeServiceTest {

    @InjectMocks
    private ResumeService resumeService;

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper modelMapper;


    @Test
    @DisplayName("이력서 작성 성공")
    @Transactional
    void SUCCESS_WRITE_RESUME() {
        Member member = Member.builder()
                .id(1L)
                .name("박호민")
                .email("ppp@test.com")
                .birth(LocalDate.parse("1996-05-09"))
                .phone("01012345678")
                .password("password")
                .emailAuth(true)
                .emailAuthDate(LocalDateTime.now())
                .userStatus(MEMBER_STATUS_ING.getStatus())
                .build();

        ResumeDto resumeDto = ResumeDto.builder()
                .memberName("박호민")
                .memberEmail("ppp@nana.com")
                .memberPhone("01012345678")
                .intro("goodㅋㅋ")
                .build();
        List<SkillDto> skillDtoList = new ArrayList<>();
        List<EducationDto> educationDtoList = new ArrayList<>();
        List<AwardDto> awardDtoList = new ArrayList<>();
        List<CertificationDto> certificationDtoList = new ArrayList<>();
        List<LanguageDto> languageDtoList = new ArrayList<>();
        List<WorkExperienceDto> workExperienceDtoList = new ArrayList<>();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();
        List<TrainingCourseDto> trainingCourseDtoList = new ArrayList<>();
        List<EtcDto> etcDtoList = new ArrayList<>();
        List<LinkDto> linkDtoList = new ArrayList<>();
        resumeDto.setSkillDtoList(skillDtoList);
        resumeDto.setEducationDtoList(educationDtoList);
        resumeDto.setAwardDtoList(awardDtoList);
        resumeDto.setCertificationDtoList(certificationDtoList);
        resumeDto.setLanguageDtoList(languageDtoList);
        resumeDto.setWorkExperienceDtoList(workExperienceDtoList);
        resumeDto.setPortfolioDtoList(portfolioDtoList);
        resumeDto.setTrainingCourseDtoList(trainingCourseDtoList);
        resumeDto.setEtcDtoList(etcDtoList);
        resumeDto.setLinkDtoList(linkDtoList);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(modelMapper.map(any(), any())).willReturn(ResponseResumeDto.builder()
                .email("ppp@nana.com")
                .name("박호민")
                .message("이력서 저장을 성공했습니다")
                .responseStatus("SUCCESS")
                .build());
        ResponseResumeDto responseResumeDto = resumeService.writeResume(resumeDto, member.getId());

        assertNotNull(responseResumeDto);
        assertEquals("SUCCESS", responseResumeDto.getResponseStatus());

    }

}