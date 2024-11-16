package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminTermSuggestionResponse {
    private final List<String> name;
    private final List<String> type;
}
