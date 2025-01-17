package com.nhatanh.centerlearn.web.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CourseFilterCriteria {
    private String keyword;
    private int sortOrder;
    private String courseType;
    private String status;
}
