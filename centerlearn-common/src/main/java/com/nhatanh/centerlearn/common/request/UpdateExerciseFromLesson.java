package com.nhatanh.centerlearn.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateExerciseFromLesson {
    private String title;
    private String content;
    private String status;
    private Long userTermId;
    private Float priority;
}
