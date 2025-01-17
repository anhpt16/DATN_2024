package com.nhatanh.centerlearn.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class WebCourseDetailResponse {
    private final long id;
    private final String code;
    private final String slug;
    private final String displayName;
    private final String courseType;
    private final int duration;
    private final String description;
    private final String apiUrl;
    private final double price;
    private final String imageApi;
    private final String managerName;
    private final String managerEmail;
    private final String managerPhone;
    private final List<WebListSubjectResponse> subjects;
}
