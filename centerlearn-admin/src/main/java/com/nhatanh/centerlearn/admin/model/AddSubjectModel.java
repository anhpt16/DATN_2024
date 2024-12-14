package com.nhatanh.centerlearn.admin.model;

import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddSubjectModel {
    private final String name;
    private final String displayName;
    private final String description;
    private final String status;
    private final String slug;
    private final long imageId;
}
