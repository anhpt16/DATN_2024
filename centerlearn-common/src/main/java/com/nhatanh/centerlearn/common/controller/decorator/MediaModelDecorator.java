package com.nhatanh.centerlearn.common.controller.decorator;

import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.response.GalleryResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class MediaModelDecorator {
    private final ModelToResponseConverter modelToResponseConverter;

    public PaginationModel<GalleryResponse> toGalleryPaginationResponse(PaginationModel<GalleryModel> galleryPaginationModel) {
        List<GalleryModel> galleryModels = galleryPaginationModel.getItems();
        List<GalleryResponse> galleryResponses = newArrayList(galleryModels, this.modelToResponseConverter::toGalleryResponse);
        return PaginationModel.<GalleryResponse>builder()
            .items(galleryResponses)
            .totalPage(galleryPaginationModel.getTotalPage())
            .currentPage(galleryPaginationModel.getCurrentPage())
            .build();
    }
}
