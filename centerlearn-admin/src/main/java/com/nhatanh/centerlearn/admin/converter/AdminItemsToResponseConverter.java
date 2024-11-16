package com.nhatanh.centerlearn.admin.converter;

import com.nhatanh.centerlearn.admin.response.AdminTermSuggestionResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;

@EzySingleton
@AllArgsConstructor
public class AdminItemsToResponseConverter {
    public AdminTermSuggestionResponse toSuggestionResponse(List<String> name, List<String> type) {
        return AdminTermSuggestionResponse.builder()
            .name(name)
            .type(type)
            .build();
    }
}
