package com.justyou.justme.domains.resume.model.entity;

import com.justyou.justme.domains.resume.dto.TrainingCourseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRAININGCOURSE")
public class TrainingCourse{
    //외부 링크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String trainingName; //교육기관명
    private String trainingText; //내용
    private LocalDate trainingStartDt; //시작일
    private LocalDate trainingEndDt; //종료일

    public static TrainingCourse of(TrainingCourseDto dto, Resume resume){
        return TrainingCourse.builder()
                .resume(resume)
                .trainingName(dto.getTrainingName())
                .trainingText(dto.getTrainingText())
                .trainingStartDt(dto.getTrainingStartDt())
                .trainingEndDt(dto.getTrainingEndDt())
                .build();
    }

}
