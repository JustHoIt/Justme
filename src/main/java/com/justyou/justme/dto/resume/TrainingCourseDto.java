package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TrainingCourseDto {
    private String trainingName; //교육기관명
    private String trainingText; //내용
    private LocalDate trainingStartDt; //시작일
    private LocalDate trainingEndDt; //종료일
}
