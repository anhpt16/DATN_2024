package com.nhatanh.centerlearn.common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateLessonRequest {
    private String title;
    private String description;
    private String status;
    private String note;
    private Long userTermId;
}
