package com.nhatanh.centerlearn.web.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonModel {
    private final long id;
    private final String title;
    private final String description;
}
