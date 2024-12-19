package com.nhatanh.centerlearn.common.response;

import com.nhatanh.centerlearn.common.enums.LessonStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class LessonResponse {
    private final long id;
    private final String title;
    private final String description;
    private final long creatorId;
    private final String creatorName;
    private final LessonStatus status;
    private final String note;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long userTermId;
    private final String userTermName;
}
