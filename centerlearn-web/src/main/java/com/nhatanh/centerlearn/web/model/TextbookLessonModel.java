package com.nhatanh.centerlearn.web.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TextbookLessonModel {
    private final long textbookId;
    private final long lessonId;
    private final float priority;
}
