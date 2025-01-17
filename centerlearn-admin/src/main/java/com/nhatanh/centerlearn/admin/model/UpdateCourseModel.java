package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCourseModel {
    private final long id;
    private final String code;
    private final String displayName;
    private final String courseType;
    private final String status;
    private final String description;
    private final Long imageId;
    private final Double price;
}
