package com.justyou.justme.domains.resume.dto;

import com.justyou.justme.domains.resume.model.entity.Etc;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EtcDto {
    private String etcTitle; //제목
    private String etcText; //내용

    public static EtcDto of(Etc etc) {
        return EtcDto.builder()
                .etcTitle(etc.getEtcTitle())
                .etcText(etc.getEtcText())
                .build();
    }
}
