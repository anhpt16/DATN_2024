package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCourseModel {
    private final String code;
    private final String displayName;
    private final String courseType;
    private final String description;
    private final long imageId;
    private final double price;
    private final String status;
    private final long creatorId;
    private final String slug;
}
