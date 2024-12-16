package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddTextbookModel {
    private final String name;
    private final String description;
    private final String author;
    private final String status;
    private final String slug;
    private final String url;
    private final long subjectId;
}
