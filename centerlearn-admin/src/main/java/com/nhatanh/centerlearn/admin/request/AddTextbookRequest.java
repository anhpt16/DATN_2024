package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddTextbookRequest {
    private String name;
    private String description;
    private String author;
    private String url;
    private long subjectId;
}
