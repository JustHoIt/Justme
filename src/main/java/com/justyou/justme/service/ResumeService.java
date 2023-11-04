package com.justyou.justme.service;


import com.justyou.justme.dto.ResponseResumeDto;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService {
    private final MemberRepository memberRepository;
    private final ResumeRepository resumeRepository;
    private final ModelMapper modelMapper;


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
}
