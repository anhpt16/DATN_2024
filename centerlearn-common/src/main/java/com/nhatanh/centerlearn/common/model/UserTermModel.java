package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTermModel {
    private final long id;
    private final long creatorId;
    private final String term;
}
