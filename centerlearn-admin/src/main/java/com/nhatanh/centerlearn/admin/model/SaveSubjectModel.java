package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveSubjectModel {
    private final long id;
    private final String name;
    private final String displayName;
    private final String description;
    private final String status;
    private final long imageId;
}
