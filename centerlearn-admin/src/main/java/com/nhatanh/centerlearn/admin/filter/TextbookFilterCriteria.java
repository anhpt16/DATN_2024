package com.nhatanh.centerlearn.admin.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TextbookFilterCriteria {
    private long id;
    private String name;
    private String author;
    private String status;
    private int sortOrder;
    private long subjectId;
}
