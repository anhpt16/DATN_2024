package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AdminCourseSubjectResponse {
    private final long id;
    private final long courseId;
    private final long subjectId;
    private final String subjectName;
    private final long textbookId;
    private final String textbookName;
}
