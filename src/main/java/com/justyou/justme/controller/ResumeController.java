package com.justyou.justme.controller;


import com.justyou.justme.dto.ResponseResumeDto;
import com.justyou.justme.dto.resume.ResumeDto;
import com.justyou.justme.security.TokenProvider;
import com.justyou.justme.service.ResumeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ResponseResumeDto> aaa1(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                  @RequestBody ResumeDto dto){
        return  ResponseEntity.ok(resumeService.writeResume(dto, tokenProvider.getUser(token).getId()));
    }

//    @ApiOperation(value = "이력서 수정")
//    @PatchMapping("/")
//    public ResponseEntity<?> aaa3(@RequestHeader(name = "X-AUTH-TOKEN") String token,
//                                  @RequestBody ResumeDto dto){
//
//    }

//    @ApiOperation(value = "이력서 삭제")
//    @DeleteMapping("/delete")
//    public ResponseEntity<?> aaa3(@RequestHeader(name = "X-AUTH-TOKEN") String token,
//                                  @RequestBody ResumeDto dto){
//
//    }

//    @ApiOperation(value = "이력서 조회")
//    @GetMapping("/")
//    public ResponseEntity<?> aaa2(@RequestHeader(name = "X-AUTH-TOKEN") String token,
//                                  @RequestBody ResumeDto dto){
//
//    }

//    @ApiOperation(value = "이력서 리스트")
//    @GetMapping("/")
//    public ResponseEntity<?> aaa3(@RequestHeader(name = "X-AUTH-TOKEN") String token,
//                                  @RequestBody ResumeDto dto){
//
//    }


}
