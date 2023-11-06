package com.justyou.justme.service;


import com.justyou.justme.dto.resume.*;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.entity.resume.*;
import com.justyou.justme.model.repository.MemberRepository;
import com.justyou.justme.model.repository.Resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService {
    private final MemberRepository memberRepository;
    private final ResumeRepository resumeRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public ResponseResumeDto writeResume(ResumeDto dto, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Resume resume = Resume.from(dto);
        resume.setMember(member);
        resume = resumeRepository.save(resume);


        ResponseResumeDto responseResumeDto = modelMapper.map(resume, ResponseResumeDto.class);
        responseResumeDto.setMessage("이력서 저장을 성공했습니다");
        responseResumeDto.setResponseStatus("SUCCESS");

        return responseResumeDto;
    }

    @Transactional
    public ResponseResumeDto updateResume(ResumeDto dto, Long resumeId, Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            //유저 정보가 있지않다면
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isEmpty()) {
            //resumeId와 일치하는 이력서 데이터가 없을시
            throw new CustomException(ErrorCode.NOT_FOUND_RESUME);
        } else if (!Objects.equals(optionalResume.get().getMember().getId(), optionalMember.get().getId())) {
            //이력서의 존재하는 memberId와 로그인한 resumeId가 일치하지 않는다면
            throw new CustomException(ErrorCode.NOT_HAVE_ACCESS_RIGHTS);
        }
        Resume resume = optionalResume.get();
        resume.setResumeTitle(dto.getResumeTitle());
        resume.setMemberName(dto.getMemberName());
        resume.setMemberEmail(dto.getMemberEmail());
        resume.setMemberPhone(dto.getMemberPhone());
        resume.setIntro(dto.getIntro());
        resume.setEducations(mapEducations(dto.getEducations(), resume));
        resume.setSkills(mapSkills(dto.getSkills(), resume));
        resume.setAwards(mapAwards(dto.getAwards(), resume));
        resume.setCertifications(mapCertifications(dto.getCertifications(), resume));
        resume.setLanguages(mapLanguages(dto.getLanguages(), resume));
        resume.setWorkExperiences(mapWorkExperiences(dto.getWorkExperiences(), resume));
        resume.setPortfolios(mapPortfolios(dto.getPortfolios(), resume));
        resume.setTrainingCourses(mapTrainingCourses(dto.getTrainingCourses(), resume));
        resume.setEtc(mapEtc(dto.getEtc(), resume));
        resume.setEtc(mapEtc(dto.getEtc(), resume));
        resume.setLinks(mapLinks(dto.getLinks(), resume));

        ResponseResumeDto responseResumeDto = modelMapper.map(resume, ResponseResumeDto.class);
        responseResumeDto.setMessage("이력서 수정을 완료했습니다.");
        responseResumeDto.setResponseStatus("SUCCESS");

        return responseResumeDto;
    }

    public Page<ResponseResumeListDto> resumeList(Long id, Pageable pageable) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        //sort = new, modify,
        Page<Resume> resumes = resumeRepository
                .findByMemberIdOrderByModifiedAtDesc(optionalMember.get().getId(), pageable);
        //수정일 내림차순으로 가져오기
        Page<ResponseResumeListDto> responseResumeList = resumes
                .map(resume -> modelMapper.map(resume, ResponseResumeListDto.class));

        return responseResumeList;
    }

    @Transactional(readOnly = true)
    public ResumeDto viewResume(Long memberId, Long resumeId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            //유저 정보가 있지않다면
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isEmpty()) {
            //resumeId와 일치하는 이력서 데이터가 없을시
            throw new CustomException(ErrorCode.NOT_FOUND_RESUME);
        } else if (!Objects.equals(optionalResume.get().getMember().getId(), optionalMember.get().getId())) {
            //이력서의 존재하는 memberId와 로그인한 resumeId가 일치하지 않는다면
            throw new CustomException(ErrorCode.NOT_HAVE_ACCESS_RIGHTS);
        }

        Resume resume = optionalResume.get();
        ResumeDto resumeDto = ResumeDto.from(resume);

        return resumeDto;
    }

    @Transactional
    public ResponseResumeDto deleteResume(Long memberId, Long resumeId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            //유저 정보가 있지않다면
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        log.info(String.valueOf(optionalResume));
        if (optionalResume.isEmpty()) {
            //resumeId와 일치하는 이력서 데이터가 없을시
            throw new CustomException(ErrorCode.NOT_FOUND_RESUME);
        } else if (!Objects.equals(optionalResume.get().getMember().getId(), optionalMember.get().getId())) {
            //이력서의 존재하는 memberId와 로그인한 resumeId가 일치하지 않는다면
            throw new CustomException(ErrorCode.NOT_HAVE_ACCESS_RIGHTS);
        }
        Resume resume = optionalResume.get();
        ResponseResumeDto responseResumeDto = modelMapper.map(resume, ResponseResumeDto.class);
        resumeRepository.deleteById(resume.getId());
        responseResumeDto.setMessage("이력서를 삭제했습니다.");
        responseResumeDto.setResponseStatus("SUCCESS");

        return responseResumeDto;
    }

    // Update 사용할 유틸리티
    private static List<Education> mapEducations(List<EducationDto> educationDtos, Resume resume) {
        return educationDtos.stream()
                .map(educationDto -> Education.of(educationDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Skill> mapSkills(List<SkillDto> skillDtoList, Resume resume) {
        return skillDtoList.stream()
                .map(skillDto -> Skill.of(skillDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Award> mapAwards(List<AwardDto> awardDtoList, Resume resume) {
        return awardDtoList.stream()
                .map(awardDto -> Award.of(awardDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Certification> mapCertifications(List<CertificationDto> certificationDtoList, Resume resume) {
        return certificationDtoList.stream()
                .map(certificationDto -> Certification.of(certificationDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Language> mapLanguages(List<LanguageDto> languageDtoList, Resume resume) {
        return languageDtoList.stream()
                .map(languageDto -> Language.of(languageDto, resume))
                .collect(Collectors.toList());
    }

    private static List<WorkExperience> mapWorkExperiences(List<WorkExperienceDto> workExperienceDtoList, Resume resume) {
        return workExperienceDtoList.stream()
                .map(workExperienceDto -> WorkExperience.of(workExperienceDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Portfolio> mapPortfolios(List<PortfolioDto> portfolioDtoList, Resume resume) {
        return portfolioDtoList.stream()
                .map(portfolioDto -> Portfolio.of(portfolioDto, resume))
                .collect(Collectors.toList());
    }

    private static List<TrainingCourse> mapTrainingCourses(List<TrainingCourseDto> trainingCourseDtoList, Resume resume) {
        return trainingCourseDtoList.stream()
                .map(trainingCourseDto -> TrainingCourse.of(trainingCourseDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Etc> mapEtc(List<EtcDto> etcDtoList, Resume resume) {
        return etcDtoList.stream()
                .map(etcDto -> Etc.of(etcDto, resume))
                .collect(Collectors.toList());
    }

    private static List<Link> mapLinks(List<LinkDto> linkDtoList, Resume resume) {
        return linkDtoList.stream()
                .map(linkDto -> Link.of(linkDto, resume))
                .collect(Collectors.toList());
    }

}
