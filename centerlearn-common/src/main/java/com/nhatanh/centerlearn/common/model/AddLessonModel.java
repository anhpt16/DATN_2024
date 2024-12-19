package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddLessonModel {
    private final String title;
    private final String description;
    private final long creatorId;
    private final String note;
    private final float priority;
    private final long userTermId;
}
