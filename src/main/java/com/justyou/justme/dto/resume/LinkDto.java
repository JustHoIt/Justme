package com.justyou.justme.dto.resume;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LinkDto {
    private String linkName; // 링크 명(ex. Google Drive)
    private String linkUrl; //링크 주소
}
