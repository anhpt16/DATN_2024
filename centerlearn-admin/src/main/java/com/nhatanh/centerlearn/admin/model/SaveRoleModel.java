package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveRoleModel {
    private final String name;
    private final String displayName;
}
