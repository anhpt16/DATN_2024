package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.response.AccountAvatarResponse;
import com.nhatanh.centerlearn.admin.response.AdminAccountDetailResponse;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.service.AccountService;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminAccountModelDecorator {
    private final AccountService accountService;
    private final MediaService mediaService;
    private final AdminModelToResponseConverter modelToResponseConverter;

    public PaginationModel<AdminAccountResponse> decorateAccountModels(PaginationModel<AccountModel> accountModelPagination) {
        List<AccountModel> accountModels = accountModelPagination.getItems();
        Set<Long> avatarIds = accountModels
            .stream()
            .map(AccountModel::getAvatarId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> avatarIdsWithNames = this.mediaService.getImageUrlMapByIds(avatarIds);
        List<AdminAccountResponse> accountResponses = newArrayList(accountModels, accountModel -> modelToResponseConverter.toAccountResponse(
            accountModel, avatarIdsWithNames.get(accountModel.getAvatarId())
        ));
        return PaginationModel.<AdminAccountResponse>builder()
            .items(accountResponses)
            .totalPage(accountModelPagination.getTotalPage())
            .currentPage(accountModelPagination.getCurrentPage())
            .build();
    }
    public AdminAccountDetailResponse decorateAccountDetailModel(AccountModel model) {
        String creatorName = "";
        if (model.getCreatorId() > 0) {
            creatorName = this.accountService.getAccountNameById(model.getCreatorId());
        }
        String avatarUrl = "";
        if (model.getAvatarId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getAvatarId());
            if (galleryModel != null) {
                avatarUrl = galleryModel.getUrl();
            }
        }
        return this.modelToResponseConverter.toAccountDetailResponse(model, creatorName, avatarUrl);
    }
    public AdminAccountResponse decorateAccountModel(AccountModel model) {
        String avatarUrl = "";
        if (model.getAvatarId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getAvatarId());
            if (galleryModel != null) {
                avatarUrl = galleryModel.getUrl();
            }
        }
        return this.modelToResponseConverter.toAccountResponse(model, avatarUrl);
    }
    public AccountAvatarResponse decorateAccountAvatar(AccountModel model) {
        String displayName = model.getDisplayName();
        String avatarUrl = "";
        if (model.getAvatarId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getAvatarId());
            if (galleryModel != null) {
                avatarUrl = galleryModel.getUrl();
            }
        }
        return this.modelToResponseConverter.toAccountAvatarResponse(displayName, avatarUrl);
    }
}
