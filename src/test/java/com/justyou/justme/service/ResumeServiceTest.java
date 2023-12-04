package com.justyou.justme.service;

import com.justyou.justme.domains.resume.dto.*;
import com.justyou.justme.domains.resume.model.repository.*;
import com.justyou.justme.domains.resume.service.ResumeService;
import com.justyou.justme.base.exception.CustomException;
import com.justyou.justme.domains.member.model.entity.Member;
import com.justyou.justme.domains.resume.model.entity.Resume;
import com.justyou.justme.domains.member.model.repository.MemberRepository;
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

import static com.justyou.justme.base.utils.MemberCode.MEMBER_STATUS_ING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeServiceTest {

    @InjectMocks
    private ResumeService resumeService;

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EducationRepository educationRepository;
    @Mock
    private SkillRepository skillRepository;

    @Mock
    private AwardRepository awardRepository;

    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private WorkExperienceRepository workExperienceRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private TrainingCourseRepository trainingCourseRepository;

    @Mock
    private EtcRepository etcRepository;

    @Mock
    private LinkRepository linkRepository;


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
    @DisplayName("이력서 저장 실패 -  찾을 수 없는 회원")
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

//        Assertions.assertThatThrownBy(() -> resumeService.writeResume(resumeDto, member.getId()
//                )).isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());

        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.writeResume(resumeDto, member.getId()));
        assertEquals("회원정보와 일치하는 회원이 없습니다.",exception.getMessage());

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
    @DisplayName("이력서 삭제 실패 - 찾을 수 없는 회원")
    @Transactional
    public void FAIL_DELETE_RESUME_NOT_FOUND_MEMBER() {
        // 실패 케이스 (유저를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.deleteResume(memberId, resumeId));
        assertEquals("회원정보와 일치하는 회원이 없습니다.",exception.getMessage());
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

        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.deleteResume(memberId, resumeId));
        assertEquals("존재하지 않는 이력서 입니다.",exception.getMessage());

    }

    @Test
    @DisplayName("이력서 보기 성공 ")
    @Transactional(readOnly = true)
    public void SUCCESS_VIEW_RESUME() {
        // 가짜 데이터 생성
        Long memberId = 1L;
        Long resumeId = 1L;

        Member member = new Member();
        member.setId(memberId);

        Resume resume = Resume.builder()
                .id(resumeId)
                .member(member)
                .memberName("박호민")
                .member(member)
                .memberEmail("phm3128@naver.com")
                .memberPhone("01038053128")
                .intro("안녕하세요 박호민입니다.")
                .build();

        // Repository의 findById 메서드 설정
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));

        // 테스트할 메서드 호출
        ResumeDto resumeDto = resumeService.viewResume(memberId, resumeId);

        // 결과 검증
        assertNotNull(resumeDto);
        assertEquals(resumeDto.getIntro(), "안녕하세요 박호민입니다.");


        // Repository의 findById 메서드 를 한 번씩 호출 했는지 검증
        verify(memberRepository, times(1)).findById(memberId);
        verify(resumeRepository, times(1)).findById(resumeId);

    }

    @Test
    @DisplayName("이력서 보기 실패 - 찾을 수 없는 회원")
    public void FAIL_VIEW_RESUME_NOT_FOUND_USER() {
        // 실패 케이스 (유저를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;

        // Repository의 findById 메서드 설정
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // 예외 결과 검증
        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.viewResume(memberId, resumeId));
        assertEquals("회원정보와 일치하는 회원이 없습니다.",exception.getMessage());


        // Repository의 findById 메서드 를 한 번씩 호출 했는지 검증
        verify(memberRepository, times(1)).findById(memberId);
        verify(resumeRepository, never()).findById(any());
    }

    @Test
    @DisplayName("이력서 보기 실패 - 찾을 수 없는 이력서")
    public void FAIL_VIEW_RESUME_NOT_FOUND_RESUME() {
        // 실패 케이스 (이력서를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;

        Member member = new Member();
        member.setId(memberId);
        // Repository의 findById 메서드 설정
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.empty());


        // 예외 결과 검증
        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.viewResume(memberId, resumeId));
        assertEquals("존재하지 않는 이력서 입니다.",exception.getMessage());
    }

    @Test
    @DisplayName("이력서 업데이트 성공")
    public void SUCCESS_UPDATE_RESUME() {
        Long memberId = 1L;
        Long resumeId = 1L;

        Member member = new Member();
        member.setId(memberId);

        Resume resume = Resume.builder()
                .id(resumeId)
                .member(member)
                .memberName("박호민")
                .member(member)
                .memberEmail("ppp@nana.com")
                .memberPhone("01038053128")
                .intro("안녕하세요 박호민입니다.")
                .build();
        resumeRepository.save(resume);

        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setMemberId(memberId);
        resumeDto.setLanguages(resumeDto.getLanguages());
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


        // Repository의 findById 메서드 설정
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        doNothing().when(educationRepository).deleteByResumeId(resumeId);
        doNothing().when(skillRepository).deleteByResumeId(resumeId);
        doNothing().when(languageRepository).deleteByResumeId(resumeId);
        doNothing().when(etcRepository).deleteByResumeId(resumeId);
        doNothing().when(linkRepository).deleteByResumeId(resumeId);
        doNothing().when(certificationRepository).deleteByResumeId(resumeId);
        doNothing().when(trainingCourseRepository).deleteByResumeId(resumeId);
        doNothing().when(workExperienceRepository).deleteByResumeId(resumeId);
        doNothing().when(portfolioRepository).deleteByResumeId(resumeId);
        given(modelMapper.map(any(), any())).willReturn(ResponseResumeDto.builder()
                .responseStatus("SUCCESS")
                .message("이력서 수정을 완료했습니다.")
                .email("ppp@nana.com")
                .name("박호민")
                .build());

        ResponseResumeDto result = resumeService.updateResume(resumeDto, resumeId, memberId);

        // 결과 검증
        assertNotNull(result);
        assertEquals("SUCCESS", result.getResponseStatus());
        assertEquals("이력서 수정을 완료했습니다.", result.getMessage());

    }

    @Test
    @DisplayName("이력서 업데이트 실패 - 찾을 수 없는 회원")
    public void FAIL_UPDATE_RESUME_NOT_FOUND_USER() {
        // 실패 케이스 (유저를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;

        ResumeDto resumeDto = new ResumeDto();
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty()); // 사용자가 없는 상황 모킹

        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.updateResume(resumeDto, memberId, resumeId));
        assertEquals("회원정보와 일치하는 회원이 없습니다.",exception.getMessage());
    }

    @Test
    @DisplayName("이력서 업데이트 실패 - 찾을 수 없는 이력서")
    public void FAIL_UPDATE_RESUME_NOT_FOUND_RESUME() {
        // 실패 케이스 (유저를 찾을 수 없음)를 가정하고 테스트
        Long memberId = 1L;
        Long resumeId = 1L;
        Member member = new Member();
        member.setId(memberId);

        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setMemberId(memberId);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member)); // 사용자가 없는 상황 모킹
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.empty()); // 이력서가 없는 상황 모킹

        CustomException exception = assertThrows(CustomException.class,
                () -> resumeService.updateResume(resumeDto, memberId, resumeId));
        assertEquals("존재하지 않는 이력서 입니다.", exception.getMessage());
    }

}