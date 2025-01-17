package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.model.CourseModel;
import com.nhatanh.centerlearn.admin.response.AdminCourseResponse;
import com.nhatanh.centerlearn.admin.service.AccountService;
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
public class AdminCourseModelDecorator {
    private final AccountService accountService;
    private final MediaService mediaService;
    private final AdminModelToResponseConverter adminModelToResponseConverter;

    public AdminCourseResponse decorateAdminCourseModel(CourseModel model) {
        String creatorName = "";
        if (model.getCreatorId() > 0) {
            AccountModel accountModel = this.accountService.getAccountById(model.getCreatorId());
            if (accountModel != null) {
                creatorName = accountModel.getDisplayName();
            }
        }
        String imageUrl = "";
        if (model.getImageId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getImageId());
            if (galleryModel != null) {
                imageUrl = galleryModel.getUrl();
            }
        }
        return adminModelToResponseConverter.toAdminCourseResponse(model, creatorName, imageUrl);
    }

    public PaginationModel<AdminCourseResponse> decorateAdminCoursePaginationModel(PaginationModel<CourseModel> coursePaginationModel) {
        List<CourseModel> courseModels = coursePaginationModel.getItems();
        Set<Long> creatorIds = courseModels.stream()
            .map(CourseModel::getCreatorId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Set<Long> imageIds = courseModels.stream()
            .map(CourseModel::getImageId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> creatorIdsWithNames = this.accountService.getCreatorNameMapByIds(creatorIds);
        Map<Long, String> imageIdsWithUrls = this.mediaService.getImageUrlMapByIds(imageIds);
        List<AdminCourseResponse> adminCourseResponses = newArrayList(courseModels, courseModel ->
            this.adminModelToResponseConverter.toAdminCourseResponse(
                courseModel,
                creatorIdsWithNames.get(courseModel.getCreatorId()),
                imageIdsWithUrls.get(courseModel.getImageId()
                )));
        return PaginationModel.<AdminCourseResponse>builder()
            .items(adminCourseResponses)
            .currentPage(coursePaginationModel.getCurrentPage())
            .totalPage(coursePaginationModel.getTotalPage())
            .build();
    }
}
