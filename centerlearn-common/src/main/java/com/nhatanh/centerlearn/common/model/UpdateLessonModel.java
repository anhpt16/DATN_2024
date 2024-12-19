package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateLessonModel {
    private final long id;
    private final String title;
    private final String description;
    private final String status;
    private final String note;
    private final Long userTermId;
}
