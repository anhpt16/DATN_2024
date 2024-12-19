package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.ExerciseStatus;
import com.nhatanh.centerlearn.common.enums.LessonStatus;
import com.nhatanh.centerlearn.common.enums.SectionStatus;
import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.response.*;
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

    public AccountResponse toAccountResponse(AccountModel accountModel, String avatarUrl) {
        String apiUrl = "";
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            apiUrl = Constants.apiImage + avatarUrl;
        }
        return AccountResponse.builder()
            .username(accountModel.getUsername())
            .displayName(accountModel.getDisplayName())
            .email(accountModel.getEmail())
            .phone(accountModel.getPhone())
            .avatarUrl(apiUrl)
            .build();
    }

    public LessonResponse toLessonResponse(LessonModel lessonModel, String name, String displayName) {
        return LessonResponse.builder()
            .id(lessonModel.getId())
            .title(lessonModel.getTitle())
            .description(lessonModel.getDescription())
            .creatorId(lessonModel.getCreatorId())
            .creatorName(displayName)
            .status(LessonStatus.fromString(lessonModel.getStatus()))
            .note(lessonModel.getNote())
            .createdAt(lessonModel.getCreatedAt())
            .updatedAt(lessonModel.getUpdatedAt())
            .userTermId(lessonModel.getUserTermId())
            .userTermName(name)
            .build();
    }

    public SectionResponse toSectionResponse(SectionModel model, String creatorName, String lessonName) {
        return SectionResponse.builder()
            .id(model.getId())
            .title(model.getTitle())
            .content(model.getContent())
            .creatorId(model.getCreatorId())
            .creatorName(creatorName)
            .status(SectionStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .lessonId(model.getLessonId())
            .lessonName(lessonName)
            .priority(model.getPriority())
            .build();
    }

    public ExerciseResponse toExerciseResponse(ExerciseModel model, String creatorName, String userTermName) {
        return ExerciseResponse.builder()
            .id(model.getId())
            .title(model.getTitle())
            .content(model.getContent())
            .creatorId(model.getCreatorId())
            .creatorName(creatorName)
            .status(ExerciseStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .userTermId(model.getUserTermId())
            .userTermName(userTermName)
            .build();
    }
}
