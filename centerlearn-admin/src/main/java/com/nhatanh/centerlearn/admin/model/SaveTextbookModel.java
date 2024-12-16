package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveTextbookModel {
    private final long id;
    private final String name;
    private final String description;
    private final String author;
    private final String url;
    private final String status;
}
