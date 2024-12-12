package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.entity.Media;
import com.nhatanh.centerlearn.common.model.ImageUploadModel;
import com.nhatanh.centerlearn.common.utils.ClockProxy;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ModelToEntityConverter {
    protected final ClockProxy clock;

    public Media toMediaEntityConverter(ImageUploadModel model) {
        Media entity = new Media();
        this.mergeToEntity(model, entity);
        return entity;
    }

    public void mergeToEntity(ImageUploadModel model, Media entity) {
        entity.setName(model.getName());
        entity.setUrl(model.getUrl());
        entity.setMediaType(model.getMediaType());
        entity.setOwnerAccountId(model.getOwnerImageId());
        entity.setDescription(model.getDescription());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setFileSize(model.getFileSize());
    }

    public void mergeToSaveEntity(Media entity, String name) {
        entity.setName(name);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveEntity(Account entity, long avatarId) {
        entity.setAvatarImageId(avatarId);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveAccountWithDisplayName(Account entity, String displayName) {
        entity.setDisplayName(displayName);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveAccountWithPhone(Account entity, String phone) {
        entity.setPhone(phone);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveAccountWithEmail(Account entity, String email) {
        entity.setEmail(email);
        entity.setUpdatedAt(this.clock.nowDateTime());
    }
}
