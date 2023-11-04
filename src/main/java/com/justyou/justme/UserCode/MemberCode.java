package com.justyou.justme.UserCode;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberCode {
    MEMBER_STATUS_REQ("REQ", "요청중"),
    MEMBER_STATUS_ING("ING", "이용중");

    private final String status;
    private final String description;

}

