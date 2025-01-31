package com.nhatanh.centerlearn.admin.converter;

import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.common.model.AddLessonModel;
import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.model.ExerciseModelWithPriority;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;

@EzySingleton
@AllArgsConstructor
public class AdminModelToModelConverter {

    public PaginationModel<AdminTermResponse> toTermResponsePagination(List<AdminTermResponse> termResponses, long totalPage, long currentPage) {
        return PaginationModel.<AdminTermResponse>builder()
            .items(termResponses)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public PaginationModel<TermModel> toTermModelPagination(List<TermModel> termModels, long totalPage, long currentPage) {
        return PaginationModel.<TermModel>builder()
            .items(termModels)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public PaginationModel<RoomModel> toRoomModelPagination(List<RoomModel> roomModels, long totalPage, long currentPage) {
        return PaginationModel.<RoomModel>builder()
            .items(roomModels)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public PaginationModel<TimeslotModel> toTimeslotModelPagination(List<TimeslotModel> timeslotModels, long totalPage, long currentPage) {
        return PaginationModel.<TimeslotModel>builder()
            .items(timeslotModels)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public AccountRoleModel toAccountRoleModelConverter(long accountId, long roleId) {
        return AccountRoleModel.builder()
            .accountId(accountId)
            .roleId(roleId)
            .build();
    }

    public SubjectTextbookModel toSubjectTextbookModelConverter(long subjectId, long textbookId) {
        return SubjectTextbookModel.builder()
            .subjectId(subjectId)
            .textbookId(textbookId)
            .build();
    }

    public TextbookLessonModel toTextbookLessonModelConverter(long textbookId, long lessonId, AddLessonModel model) {
        return TextbookLessonModel.builder()
            .lessonId(lessonId)
            .textbookId(textbookId)
            .priority(model.getPriority())
            .build();
    }

    public TextbookLessonModel toTextbookLessonModelConverter(long textbookId, long lessonId, float priority) {
        return TextbookLessonModel.builder()
            .lessonId(lessonId)
            .textbookId(textbookId)
            .priority(priority)
            .build();
    }

    public ExerciseModelWithPriority toExerciseModelWithPriority(ExerciseModel exerciseModel, float priority) {
        return ExerciseModelWithPriority.builder()
            .id(exerciseModel.getId())
            .title(exerciseModel.getTitle())
            .content(exerciseModel.getContent())
            .creatorId(exerciseModel.getCreatorId())
            .status(exerciseModel.getStatus())
            .createdAt(exerciseModel.getCreatedAt())
            .updatedAt(exerciseModel.getUpdatedAt())
            .userTermId(exerciseModel.getUserTermId())
            .priority(priority)
            .build();
    }
}
