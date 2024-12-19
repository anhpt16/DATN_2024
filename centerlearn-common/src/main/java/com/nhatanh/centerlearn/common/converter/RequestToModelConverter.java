package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.enums.ExerciseStatus;
import com.nhatanh.centerlearn.common.enums.SectionStatus;
import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.request.*;
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

    public UpdateLessonModel toLessonModel(UpdateLessonRequest request, long lessonId) {
        return UpdateLessonModel.builder()
            .id(lessonId)
            .title(request.getTitle())
            .description(request.getDescription())
            .note(request.getNote())
            .status(request.getStatus())
            .userTermId(request.getUserTermId())
            .build();
    }

    public AddLessonModel toAddLessonModel(AddLessonRequest request, long creatorId) {
        return AddLessonModel.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .creatorId(creatorId)
            .note(request.getNote())
            .userTermId(request.getUserTermId())
            .build();
    }

    public AddSectionModel toAddSectionModel(AddSectionRequest request, long accountId, long lessonId) {
        return AddSectionModel.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .creatorId(accountId)
            .status(SectionStatus.ACTIVE.name())
            .lessonId(lessonId)
            .priority(request.getPriority())
            .build();
    }

    public AddExerciseModel toAddExerciseModel(AddExerciseRequest request, long creatorId) {
        return AddExerciseModel.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .creatorId(creatorId)
            .status(ExerciseStatus.ACTIVE.name())
            .userTermId(request.getUserTermId())
            .build();
    }

    public LessonExerciseModel toLessonExerciseModel(long lessonId, long exerciseId, float priority) {
        return LessonExerciseModel.builder()
            .lessonId(lessonId)
            .exerciseId(exerciseId)
            .priority(priority)
            .build();
    }

    public SaveSectionModel toSaveSectionModel(SaveSectionRequest request, long sectionId) {
        return SaveSectionModel.builder()
            .id(sectionId)
            .title(request.getTitle())
            .content(request.getContent())
            .status(request.getStatus())
            .lessonId(request.getLessonId())
            .priority(request.getPriority())
            .build();
    }

    public UpdateExerciseModel toUpdateExerciseModel(UpdateExerciseRequest request, long exerciseId) {
        return UpdateExerciseModel.builder()
            .id(exerciseId)
            .title(request.getTitle())
            .content(request.getContent())
            .status(request.getStatus())
            .userTermId(request.getUserTermId())
            .build();
    }
}
