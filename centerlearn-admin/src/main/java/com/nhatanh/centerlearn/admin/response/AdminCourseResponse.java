package com.nhatanh.centerlearn.admin.response;

import com.nhatanh.centerlearn.common.enums.CourseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class AdminCourseResponse {
    private final long id;
    private final String code;
    private final String displayName;
    private final String courseType;
    private final int duration;
    private final String description;
    private final CourseStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long creatorId;
    private final String creatorName;
    private final String apiUrl;
    private final String slug;
    private final double price;
    private final long managerId;
}
