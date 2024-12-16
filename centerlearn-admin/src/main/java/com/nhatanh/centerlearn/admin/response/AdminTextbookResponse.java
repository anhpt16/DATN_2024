package com.nhatanh.centerlearn.admin.response;

import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminTextbookResponse {
    private final long id;
    private final String name;
    private final String description;
    private final String author;
    private final String url;
    private final TextbookStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String slug;
}
