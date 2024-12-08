package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Media;
import com.nhatanh.centerlearn.common.model.ImageUploadModel;
import com.nhatanh.centerlearn.common.repository.MediaRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaService {
    private final MediaRepository mediaRepository;
    private final ModelToEntityConverter modelToEntityConverter;

    public long uploadImage(ImageUploadModel model) {
        Media media = this.modelToEntityConverter.toMediaEntityConverter(model);
        this.mediaRepository.save(media);
        return media.getId();
    }
}
