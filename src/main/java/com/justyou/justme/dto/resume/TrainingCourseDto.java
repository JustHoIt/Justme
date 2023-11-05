package com.justyou.justme.dto.resume;

import com.justyou.justme.model.entity.resume.TrainingCourse;
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

    public static TrainingCourseDto of(TrainingCourse trainingCourse) {
        return TrainingCourseDto.builder()
                .trainingName(trainingCourse.getTrainingName())
                .trainingText(trainingCourse.getTrainingText())
                .trainingStartDt(trainingCourse.getTrainingStartDt())
                .trainingEndDt(trainingCourse.getTrainingEndDt())
                .build();
    }
}
