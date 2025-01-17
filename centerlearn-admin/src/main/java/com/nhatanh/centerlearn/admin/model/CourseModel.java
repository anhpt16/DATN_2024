package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CourseModel {
    private final long id;
    private final String code;
    private final String displayName;
    private final String courseType;
    private final int duration;
    private final String description;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long creatorId;
    private final long imageId;
    private final String slug;
    private final double price;
    private final long managerId;
}
