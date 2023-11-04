package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.TrainingCourseDto;
import com.justyou.justme.model.entity.BaseEntity;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRAININGCOURSE")
@AuditOverride(forClass = BaseEntity.class)
public class TrainingCourse extends BaseEntity {
    //외부 링크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String trainingName; //교육기관명
    private String trainingText; //내용
    private LocalDate trainingStartDt; //시작일
    private LocalDate trainingEndDt; //종료일

    public static TrainingCourse of(TrainingCourseDto dto){
        return TrainingCourse.builder()
                .trainingName(dto.getTrainingName())
                .trainingText(dto.getTrainingText())
                .trainingStartDt(dto.getTrainingStartDt())
                .trainingEndDt(dto.getTrainingEndDt())
                .build();
    }

}