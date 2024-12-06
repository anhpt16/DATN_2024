package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AccountAvatarResponse {
    private final String displayName;
    private final String userImageUrl;
}
