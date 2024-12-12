package com.nhatanh.centerlearn.common.controller.decorator;

import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.response.AccountResponse;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class AccountModelDecorator {
    private final MediaService mediaService;
    private final ModelToResponseConverter modelToResponseConverter;

    public AccountResponse decorateAccountModel(AccountModel accountModel) {
        if (accountModel == null) {
            return null;
        }
        String avatarUrl = "";
        if (accountModel.getAvatarId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(accountModel.getAvatarId());
            if (galleryModel != null) {
                avatarUrl = galleryModel.getUrl();
            }
        }
        return this.modelToResponseConverter.toAccountResponse(accountModel, avatarUrl);
    }
}
