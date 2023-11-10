package com.justyou.justme.service;

import com.justyou.justme.dto.resume.*;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.MySQL.Member;
import com.justyou.justme.model.entity.MySQL.resume.Resume;
import com.justyou.justme.model.repository.MemberRepository;
import com.justyou.justme.model.repository.Resume.ResumeRepository;
import org.assertj.core.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

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
        resumeDto.setSkills(skillDtoList);
        resumeDto.setEducations(educationDtoList);
        resumeDto.setAwards(awardDtoList);
        resumeDto.setCertifications(certificationDtoList);
        resumeDto.setLanguages(languageDtoList);
        resumeDto.setWorkExperiences(workExperienceDtoList);
        resumeDto.setPortfolios(portfolioDtoList);
        resumeDto.setTrainingCourses(trainingCourseDtoList);
        resumeDto.setEtc(etcDtoList);
        resumeDto.setLinks(linkDtoList);

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

    @Test
    @DisplayName("이력서 저장 실패")
    @Transactional
    void FAIL_WRITE_RESUME() {

        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

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
        resumeDto.setSkills(skillDtoList);
        resumeDto.setEducations(educationDtoList);
        resumeDto.setAwards(awardDtoList);
        resumeDto.setCertifications(certificationDtoList);
        resumeDto.setLanguages(languageDtoList);
        resumeDto.setWorkExperiences(workExperienceDtoList);
        resumeDto.setPortfolios(portfolioDtoList);
        resumeDto.setTrainingCourses(trainingCourseDtoList);
        resumeDto.setEtc(etcDtoList);
        resumeDto.setLinks(linkDtoList);

        Assertions.assertThatThrownBy(() -> resumeService.writeResume(resumeDto, member.getId()
                )).isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());

    }

    @Test
    @DisplayName("이력서 삭제")
    @Transactional
    public void SUCCESS_DELETE_RESUME() {
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
        Resume resume = Resume.builder()
                .id(1L)
                .memberName("박호민")
                .member(member)
                .memberEmail("phm3128@naver.com")
                .memberPhone("01038053128")
                .intro("안녕하세요 박호민입니다.")
                .build();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(resumeRepository.findById(anyLong())).willReturn(Optional.of(resume));
        given(modelMapper.map(any(), any())).willReturn(ResponseResumeDto.builder()
                .email("pppp@test.com")
                .name("박호민")
                .message("이력서를 삭제했습니다.")
                .responseStatus("SUCCESS")
                .build());

        ResponseResumeDto responseResumeDto = resumeService.deleteResume(member.getId(), resume.getId());

        assertNotNull(responseResumeDto);
        assertEquals("SUCCESS", responseResumeDto.getResponseStatus());
        assertEquals("이력서를 삭제했습니다.", responseResumeDto.getMessage());

    }

    @Test
    @DisplayName("이력서 삭제 실패 - 찾을 수 없는 유저")
    @Transactional
    public void FAIL_DELETE_RESUME_NOT_FOUND_MEMBER() {
        // 실패 케이스 (유저를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> resumeService.deleteResume(memberId, resumeId));
    }

    @Test
    @DisplayName("이력서 삭제 실패 - 찾을 수 없는 이력서")
    @Transactional
    public void FAIL_DELETE_RESUME_NOT_FOUND_RESUME() {
        // 실패 케이스 (이력서를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;

        Member member = new Member();
        member.setId(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> resumeService.deleteResume(memberId, resumeId));
    }

}