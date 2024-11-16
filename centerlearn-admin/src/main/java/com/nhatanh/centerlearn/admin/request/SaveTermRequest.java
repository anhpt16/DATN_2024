package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaveTermRequest {
    private String name;
    private String termType;
    private String parentName;
    private String parentType;
    private String description;
}
