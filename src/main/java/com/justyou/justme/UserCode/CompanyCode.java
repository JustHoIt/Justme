package com.justyou.justme.UserCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyCode {
    MEMBER_STATUS_REQ("REQ", "인증전"),
    MEMBER_STATUS_ING("ING", "이중 완료"),
    MEMBER_STATUS_STOP("WITHDRAW", "탈퇴");

    private final String status;
    private final String description;
}
