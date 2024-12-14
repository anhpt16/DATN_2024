package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.SubjectModel;
import com.nhatanh.centerlearn.admin.response.AdminSubjectResponse;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.request.ImageNameRequest;
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
public class AdminSubjectModelDecorator {
    private final MediaService mediaService;
    private final AdminModelToResponseConverter adminModelToResponseConverter;

    public PaginationModel<AdminSubjectResponse> decorateSubjectPaginationModel(PaginationModel<SubjectModel> subjectPaginationModel) {
        List<SubjectModel> subjectModels = subjectPaginationModel.getItems();
        Set<Long> imageIds = subjectModels.stream()
            .map(SubjectModel::getImageId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> imageIdsWithName = this.mediaService.getImageUrlMapByIds(imageIds);
        List<AdminSubjectResponse> subjectResponses = newArrayList(subjectModels, subjectModel -> this.adminModelToResponseConverter.toSubjectResponse(subjectModel, imageIdsWithName.get(subjectModel.getImageId())));
        return PaginationModel.<AdminSubjectResponse>builder()
            .items(subjectResponses)
            .currentPage(subjectPaginationModel.getCurrentPage())
            .totalPage(subjectPaginationModel.getTotalPage())
            .build();
    }

    public AdminSubjectResponse decorateSubjectModel(SubjectModel model) {
        String imageUrl = "";
        if (model.getImageId() > 0) {
            GalleryModel galleryModel = this.mediaService.getGalleryById(model.getImageId());
            if (galleryModel != null) {
                imageUrl = galleryModel.getUrl();
            }
        }
        return this.adminModelToResponseConverter.toSubjectResponse(model, imageUrl);
    }
}
