package com.nhatanh.centerlearn.admin.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SubjectFilterCriteria {
    private String name;
    private String displayName;
    private String status;
    private int sortOrder;
}
