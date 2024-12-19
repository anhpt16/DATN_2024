package com.nhatanh.centerlearn.common.response;

import com.nhatanh.centerlearn.common.enums.ExerciseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ExerciseResponse {
    private final long id;
    private final String title;
    private final String content;
    private final long creatorId;
    private final String creatorName;
    private final ExerciseStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long userTermId;
    private final String userTermName;
}
