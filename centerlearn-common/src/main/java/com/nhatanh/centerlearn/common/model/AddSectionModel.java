package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddSectionModel {
    private final String title;
    private final String content;
    private final long creatorId;
    private final String status;
    private final long lessonId;
    private final float priority;
}
