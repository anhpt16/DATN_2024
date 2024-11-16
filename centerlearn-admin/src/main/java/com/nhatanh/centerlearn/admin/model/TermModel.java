package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TermModel {
    private final long id;
    private final String name;
    private final String slug;
    private final String termType;
    private final long parentId;
    private final String description;
}
