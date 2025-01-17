package com.nhatanh.centerlearn.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AdminListSectionResponse {
    private final long id;
    private final String title;
    private final float priority;
}
