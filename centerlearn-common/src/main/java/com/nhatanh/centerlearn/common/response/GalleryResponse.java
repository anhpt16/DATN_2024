package com.nhatanh.centerlearn.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class GalleryResponse {
    private final long id;
    private final String name;
    private final String url;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final double fileSize;
}
