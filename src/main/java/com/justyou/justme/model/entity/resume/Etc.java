package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.EtcDto;
import com.justyou.justme.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ETC")
public class Etc{
    //기타
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String etcTitle; //제목
    private String etcText; //내용

    public static Etc of(EtcDto dto, Resume resume){
        return Etc.builder()
                .resume(resume)
                .etcTitle(dto.getEtcTitle())
                .etcText(dto.getEtcText())
                .build();
    }
}
