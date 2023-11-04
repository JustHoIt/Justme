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
public class Etc extends BaseEntity {
    //기타
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String etcTitle; //제목
    private String etcText; //내용

    public static Etc of(EtcDto dto){
        return Etc.builder()
                .etcTitle(dto.getEtcTitle())
                .etcText(dto.getEtcText())
                .build();
    }
}
