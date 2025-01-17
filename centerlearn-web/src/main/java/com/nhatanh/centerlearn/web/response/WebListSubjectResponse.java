package com.nhatanh.centerlearn.web.response;

import com.nhatanh.centerlearn.common.response.LessonResponse;
import com.nhatanh.centerlearn.web.model.LessonModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class WebListSubjectResponse {
    private final String subjectName;
    private final List<LessonModel> lessons;
}
