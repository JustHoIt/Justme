package com.justyou.justme.service;


import com.justyou.justme.dto.UserDto;
import com.justyou.justme.dto.resume.ResponseResumeDto;
import com.justyou.justme.dto.resume.ResponseResumeListDto;
import com.justyou.justme.dto.resume.ResumeDto;
import com.justyou.justme.exception.CustomException;
import com.justyou.justme.exception.ErrorCode;
import com.justyou.justme.model.entity.Member;
import com.justyou.justme.model.entity.resume.Resume;
import com.justyou.justme.model.repository.MemberRepository;
import com.justyou.justme.model.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

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
        log.info(String.valueOf(optionalResume));
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
        ResponseResumeDto responseResumeDto =modelMapper.map(resume, ResponseResumeDto.class);
        resumeRepository.deleteById(resume.getId());
        responseResumeDto.setMessage("이력서를 삭제했습니다.");
        responseResumeDto.setResponseStatus("SUCCESS");

        return responseResumeDto;
    }
}
