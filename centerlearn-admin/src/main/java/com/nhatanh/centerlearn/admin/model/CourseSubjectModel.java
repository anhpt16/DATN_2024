package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseSubjectModel {
    private final long id;
    private final long courseId;
    private final long subjectId;
    private final long textbookId;
}
