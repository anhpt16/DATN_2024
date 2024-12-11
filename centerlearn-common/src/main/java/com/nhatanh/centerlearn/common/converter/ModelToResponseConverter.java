package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.response.GalleryResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class ModelToResponseConverter {

    public GalleryResponse toGalleryResponse (GalleryModel model) {
        if (model == null) {
            return null;
        }
        String apiUrl = Constants.apiImage + model.getUrl();
        return GalleryResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .url(apiUrl)
            .description(model.getDescription())
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .fileSize(model.getFileSize())
            .build();
    }

    public List<GalleryResponse> toGalleryListResponse (List<GalleryModel> models) {
        if (models.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(models, this::toGalleryResponse);
    }
}
