package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SaveSectionModel {
    private final long id;
    private final String title;
    private final String content;
    private final String status;
    private final Long lessonId;
    private final Float priority;
}
