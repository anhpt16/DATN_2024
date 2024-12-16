package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubjectTextbookModel {
    private final long subjectId;
    private final long textbookId;
}
