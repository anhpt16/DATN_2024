package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SectionModel {
    private final long id;
    private final String title;
    private final String content;
    private final long creatorId;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long lessonId;
    private final float priority;
}
