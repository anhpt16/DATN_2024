package com.nhatanh.centerlearn.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddLessonRequest {
    private String title;
    private String description;
    private String note;
    private float priority;
    private long userTermId;
}
