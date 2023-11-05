package com.justyou.justme.controller;


import com.justyou.justme.dto.resume.ResponseResumeDto;
import com.justyou.justme.dto.resume.ResponseResumeListDto;
import com.justyou.justme.dto.resume.ResumeDto;
import com.justyou.justme.security.TokenProvider;
import com.justyou.justme.service.ResumeService;
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
    @DeleteMapping("/delete{resumeId}")
    public ResponseEntity<ResponseResumeDto> deleteResume(@RequestHeader(name = "auth-token") String token,
                                                          @PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeService.deleteResume(tokenProvider.getUser(token).getId(), resumeId));
    }

//    @ApiOperation(value = "이력서 수정")
//    @PatchMapping("/")
//    public ResponseEntity<?> aaa3(@RequestHeader(name = "auth-token") String token,
//                                  @RequestBody ResumeDto dto){
//
//    }


}
