package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddCourseRequest {
    private String code;
    private String displayName;
    private String courseType;
    private String description;
    private long imageId;
    private double price;
}
