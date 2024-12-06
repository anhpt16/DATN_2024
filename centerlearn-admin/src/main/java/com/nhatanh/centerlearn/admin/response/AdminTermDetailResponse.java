package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AdminTermDetailResponse {
    private final long id;
    private final String name;
    private final String slug;
    private final String termType;
    private final String parentName;
    private final String parentType;
    private final String description;
}
