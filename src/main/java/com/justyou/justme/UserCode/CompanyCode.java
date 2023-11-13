package com.justyou.justme.UserCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyCode {
    COMPANY_STATUS_REQ("REQ", "인증전"),
    COMPANY_STATUS_ING("ING", "이중 완료");

    private final String status;
    private final String description;
}
