package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminTextbookShortResponse {
    private final long id;
    private final String name;
    private final String author;
}
