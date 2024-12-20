package com.nhatanh.centerlearn.admin.converter;


import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.*;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.*;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

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
}
