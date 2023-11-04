package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EtcDto {
    private String etcTitle; //제목
    private String etcText; //내용
}
