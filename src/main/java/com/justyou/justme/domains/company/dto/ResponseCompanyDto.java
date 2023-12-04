package com.justyou.justme.domains.company.dto;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCompanyDto {
    private String name;
    private String message;
    private String responseStatus;
}
