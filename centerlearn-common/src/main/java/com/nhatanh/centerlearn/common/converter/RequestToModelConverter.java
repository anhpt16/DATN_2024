package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.model.ImageUploadModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class RequestToModelConverter {

    public ImageUploadModel toImageUploadModel (
        String name,
        String url,
        String mediaType,
        long ownerAccountId,
        long fileSize
    ) {
        return ImageUploadModel.builder()
            .name(name)
            .url(url)
            .mediaType(mediaType)
            .ownerImageId(ownerAccountId)
            .fileSize(fileSize)
            .build();
    }
}
