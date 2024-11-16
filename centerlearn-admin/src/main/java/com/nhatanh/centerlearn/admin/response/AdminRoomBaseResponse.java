package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AdminRoomBaseResponse {
    private final long id;
    private final String name;
}
