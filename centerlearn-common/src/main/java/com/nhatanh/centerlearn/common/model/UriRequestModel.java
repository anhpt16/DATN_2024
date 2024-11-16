package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UriRequestModel {
    private final long roleId;
    private final String path;
    private final String method;
}
