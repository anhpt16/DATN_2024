package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateCourseRequest {
    private String code;
    private String displayName;
    private String courseType;
    private String status;
    private String description;
    private Long imageId;
    private Double price;
}
