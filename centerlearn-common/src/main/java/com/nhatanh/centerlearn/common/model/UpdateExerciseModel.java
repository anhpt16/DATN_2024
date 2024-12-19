package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateExerciseModel {
    private final long id;
    private final String title;
    private final String content;
    private final String status;
    private final Long userTermId;
}
