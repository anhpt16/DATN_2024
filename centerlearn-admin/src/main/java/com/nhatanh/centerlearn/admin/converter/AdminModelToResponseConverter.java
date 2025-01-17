package com.nhatanh.centerlearn.admin.converter;


import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.*;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.*;
import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.model.ExerciseModelWithPriority;
import com.nhatanh.centerlearn.common.model.LessonModel;
import com.nhatanh.centerlearn.common.model.SectionModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@EzySingleton
@AllArgsConstructor
public class AdminModelToResponseConverter {

    public AdminTermResponse toTermItemResponse(TermModel termModel, String name) {
        return AdminTermResponse.builder()
            .id(termModel.getId())
            .name(termModel.getName())
            .slug(termModel.getSlug())
            .termType(termModel.getTermType())
            .parentName(name)
            .description(termModel.getDescription())
            .build();
    }

    public AdminTermDetailResponse toTermDetailResponse(TermModel termModel, String name, String parentType) {
        return AdminTermDetailResponse.builder()
            .id(termModel.getId())
            .name(termModel.getName())
            .slug(termModel.getSlug())
            .termType(termModel.getTermType())
            .parentName(name)
            .parentType(parentType)
            .description(termModel.getDescription())
            .build();
    }

    public AdminRoomResponse toRoomItemResponse(RoomModel roomModel, String name) {
        return AdminRoomResponse.builder()
            .id(roomModel.getId())
            .name(roomModel.getName())
            .slot(roomModel.getSlot())
            .description(roomModel.getDescription())
            .termName(name)
            .status(RoomStatus.fromString(roomModel.getStatus()))
            .createdAt(roomModel.getCreatedAt())
            .updatedAt(roomModel.getUpdatedAt())
            .build();
    }

    public AdminRoomBaseResponse toRoomBaseResponse(TermModel model) {
        return AdminRoomBaseResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .build();
    }

    public AdminTimeslotResponse toTimeslotResponse(TimeslotModel model) {
        return AdminTimeslotResponse.builder()
            .id(model.getId())
            .period(model.getPeriod())
            .timeStart(model.getTimeStart())
            .timeEnd(model.getTimeEnd())
            .status(TimeslotStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .description(model.getDescription())
            .build();
    }

    public AdminRoleResponse toRoleResponse(RoleModel model) {
        return AdminRoleResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .displayName(model.getDisplayName())
            .createdAt(model.getCreatedAt())
            .build();
    }

    public AdminRoleNameResponse toRoleNameResponse(RoleModel roleModel) {
        return AdminRoleNameResponse.builder()
            .id(roleModel.getId())
            .name(roleModel.getName())
            .build();
    }

    public AdminAccountResponse toAccountResponse(AccountModel model, String avatarUrl) {
        String apiUrl = "";
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            apiUrl = Constants.apiImage + avatarUrl;
        }
        return AdminAccountResponse.builder()
            .id(model.getId())
            .username(model.getUsername())
            .displayName(model.getDisplayName())
            .email(model.getEmail())
            .phone(model.getPhone())
            .status(AccountStatus.fromString(model.getStatus()))
            .avatarUrl(apiUrl)
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .build();
    }

    public AdminAccountDetailResponse toAccountDetailResponse(AccountModel model, String creatorName, String avatarUrl) {
        String apiUrl = "";
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            apiUrl = Constants.apiImage + avatarUrl;
        }
        return AdminAccountDetailResponse.builder()
            .id(model.getId())
            .username(model.getUsername())
            .displayName(model.getDisplayName())
            .email(model.getEmail())
            .phone(model.getPhone())
            .status(AccountStatus.fromString(model.getStatus()))
            .avatarUrl(apiUrl)
            .creatorId(model.getCreatorId())
            .creatorName(creatorName)
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .build();
    }

    public AccountAvatarResponse toAccountAvatarResponse(String displayName, String avatarUrl) {
        String apiUrl = "";
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            apiUrl = Constants.apiImage + avatarUrl;
        }
        return AccountAvatarResponse.builder()
            .displayName(displayName)
            .userImageUrl(apiUrl)
            .build();
    }

    public AdminSubjectResponse toSubjectResponse(SubjectModel model, String imageUrl) {
        String apiUrl = "";
        if (imageUrl != null && !imageUrl.isEmpty()) {
            apiUrl = Constants.apiImage + imageUrl;
        }
        return AdminSubjectResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .displayName(model.getDisplayName())
            .description(model.getDescription())
            .status(SubjectStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .slug(model.getSlug())
            .imageUrl(apiUrl)
            .build();
    }

    public AdminTextbookResponse toTextbookResponse(TextbookModel model) {
        return AdminTextbookResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .description(model.getDescription())
            .author(model.getAuthor())
            .status(TextbookStatus.fromString(model.getStatus()))
            .url(model.getUrl())
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .slug(model.getSlug())
            .build();
    }

    public AdminSubjectShortResponse toSubjectShortResponse(SubjectModel model) {
        return AdminSubjectShortResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .displayName(model.getDisplayName())
            .build();
    }

    public AdminTextbookShortResponse toTextbookShortResponse(TextbookModel model) {
        return AdminTextbookShortResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .author(model.getAuthor())
            .build();
    }

    public AdminListSectionResponse toListSectionResponse(SectionModel model) {
        return AdminListSectionResponse.builder()
            .id(model.getId())
            .title(model.getTitle())
            .priority(model.getPriority())
            .build();
    }

    public AdminListExerciseResponse toListExerciseResponse(ExerciseModelWithPriority model) {
        return AdminListExerciseResponse.builder()
            .id(model.getId())
            .title(model.getTitle())
            .priority(model.getPriority())
            .build();
    }

    public AdminLessonSectionResponse toAdminLessonSectionResponse(LessonModel lessonModel, List<AdminListSectionResponse> adminListSectionResponses, float lessonPriority) {
        return AdminLessonSectionResponse.builder()
            .id(lessonModel.getId())
            .title(lessonModel.getTitle())
            .priority(lessonPriority)
            .sections(adminListSectionResponses)
            .build();
    }

    public AdminLessonExerciseResponse toAdminLessonExerciseResponse(LessonModel lessonModel, List<AdminListExerciseResponse> adminListExerciseResponses, float priority) {
        return AdminLessonExerciseResponse.builder()
            .id(lessonModel.getId())
            .title(lessonModel.getTitle())
            .priority(priority)
            .exercises(adminListExerciseResponses)
            .build();
    }

    public AdminCourseResponse toAdminCourseResponse(CourseModel model, String creatorName, String imageUrl) {
        String apiUrl = "";
        String creator = "";
        if (imageUrl != null && !imageUrl.isEmpty()) {
            apiUrl = Constants.apiImage + imageUrl;
        }
        if (creatorName != null) {
            creator= creatorName;
        }
        return AdminCourseResponse.builder()
            .id(model.getId())
            .code(model.getCode())
            .displayName(model.getDisplayName())
            .courseType(CourseType.fromString(model.getCourseType()).getDisplayName())
            .duration(model.getDuration())
            .description(model.getDescription())
            .status(CourseStatus.fromString(model.getStatus()))
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .creatorId(model.getCreatorId())
            .creatorName(creator)
            .apiUrl(apiUrl)
            .slug(model.getSlug())
            .price(model.getPrice())
            .managerId(model.getManagerId())
            .build();
    }

    public AdminCourseSubjectResponse toCourseSubjectResponse(CourseSubjectModel courseSubjectModel, String subjectName, String textbookName) {
        return AdminCourseSubjectResponse.builder()
            .id(courseSubjectModel.getId())
            .courseId(courseSubjectModel.getCourseId())
            .subjectId(courseSubjectModel.getSubjectId())
            .subjectName(subjectName)
            .textbookId(courseSubjectModel.getTextbookId())
            .textbookName(textbookName)
            .build();
    }
}
