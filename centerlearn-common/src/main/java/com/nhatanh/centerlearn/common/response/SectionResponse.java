package com.nhatanh.centerlearn.common.response;

import com.nhatanh.centerlearn.common.enums.SectionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class SectionResponse {
    private final long id;
    private final String title;
    private final String content;
    private final long creatorId;
    private final String creatorName;
    private final SectionStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long lessonId;
    private final String lessonName;
    private final float priority;
}
