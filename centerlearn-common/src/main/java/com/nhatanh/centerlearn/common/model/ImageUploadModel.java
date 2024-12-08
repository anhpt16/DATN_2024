package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ImageUploadModel {
    private final String name;
    private final String url;
    private final String mediaType;
    private final String description;
    private final long ownerImageId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long fileSize;
}
