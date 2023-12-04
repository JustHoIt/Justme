package com.justyou.justme.domains.resume.controller;


import com.justyou.justme.domains.resume.service.ResumeService;
import com.justyou.justme.domains.resume.dto.ResponseResumeDto;
import com.justyou.justme.domains.resume.dto.ResponseResumeListDto;
import com.justyou.justme.domains.resume.dto.ResumeDto;
import com.justyou.justme.base.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cv")
public class ResumeController {

    private final ResumeService resumeService;

    private final TokenProvider tokenProvider;

    @ApiOperation(value = "이력서 작성")
    @PostMapping("/write")
    public ResponseEntity<ResponseResumeDto> writeResume(@RequestHeader(name = "auth-token") String token,
                                                         @RequestBody ResumeDto dto) {
        return ResponseEntity.ok(resumeService.writeResume(dto, tokenProvider.getUser(token).getId()));
    }

    @ApiOperation(value = "이력서 수정")
    @PutMapping("/{resumeId}")
    public ResponseEntity<?> updateResume(@RequestHeader(name = "auth-token") String token,
                                          @PathVariable Long resumeId , @RequestBody ResumeDto dto){
        return ResponseEntity.ok(resumeService.updateResume(dto, resumeId, tokenProvider.getUser(token).getId()));
    }

    @ApiOperation(value = "이력서 리스트")
    @GetMapping("/resumeList")
    public ResponseEntity<Page<ResponseResumeListDto>> listResume(@RequestHeader(name = "auth-token") String token
            , final Pageable pageable) {
        return ResponseEntity.ok(resumeService.resumeList(tokenProvider.getUser(token).getId(), pageable));

    }

    @ApiOperation(value = "이력서 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> viewResume(@RequestHeader(name = "auth-token") String token, @PathVariable Long id) {
        return ResponseEntity.ok(resumeService.viewResume(tokenProvider.getUser(token).getId(), id));

    }

    @ApiOperation(value = "이력서 삭제")
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<ResponseResumeDto> deleteResume(@RequestHeader(name = "auth-token") String token,
                                                          @PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeService.deleteResume(tokenProvider.getUser(token).getId(), resumeId));
    }

}
