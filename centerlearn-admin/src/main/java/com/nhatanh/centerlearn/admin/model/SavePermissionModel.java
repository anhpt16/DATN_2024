package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavePermissionModel {
    private final long roleId;
    private final String featureUri;
    private final String featureMethod;
}
