package com.nhatanh.centerlearn.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddExerciseRequest {
    private String title;
    private String content;
    private long userTermId;
    private float priority;
}
