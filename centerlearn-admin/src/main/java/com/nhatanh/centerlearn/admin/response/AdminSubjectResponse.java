package com.nhatanh.centerlearn.admin.response;

import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class AdminSubjectResponse {
    private final long id;
    private final String name;
    private final String displayName;
    private final String description;
    private final SubjectStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String imageUrl;
    private final String slug;
}
