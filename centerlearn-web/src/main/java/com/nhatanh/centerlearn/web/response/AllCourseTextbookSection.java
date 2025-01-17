package com.nhatanh.centerlearn.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class AllCourseTextbookSection {
    private final long subjectId;
    private final String SubjectName;
    private final List<AdminLessonSectionResponse> lessonsSections;
}
