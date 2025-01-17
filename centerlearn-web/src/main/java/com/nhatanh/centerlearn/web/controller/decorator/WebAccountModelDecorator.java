package com.nhatanh.centerlearn.web.controller.decorator;

import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.web.converter.WebModelToResponseConverter;
import com.nhatanh.centerlearn.web.model.AccountModel;
import com.nhatanh.centerlearn.web.response.AccountAvatarResponse;
import com.nhatanh.centerlearn.web.service.AccountService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class WebAccountModelDecorator {
    private final AccountService accountService;
    private final MediaService mediaService;
    private final WebModelToResponseConverter webModelToResponseConverter;


    public AccountAvatarResponse decorateAccountAvatar(AccountModel model) {
        String displayName = model.getDisplayName();
        String avatarUrl = "";
        if (model.getAvatarId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getAvatarId());
            if (galleryModel != null) {
                avatarUrl = galleryModel.getUrl();
            }
        }
        return this.webModelToResponseConverter.toAccountAvatarResponse(displayName, avatarUrl);
    }
}
