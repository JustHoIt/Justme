package com.justyou.justme.domains.resume.dto;


import com.justyou.justme.domains.resume.model.entity.Link;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LinkDto {
    private String linkName; // 링크 명(ex. Google Drive)
    private String linkUrl; //링크 주소

    public static LinkDto of(Link link) {
        return LinkDto.builder()
                .linkName(link.getLinkName())
                .linkUrl(link.getLinkUrl())
                .build();
    }
}
