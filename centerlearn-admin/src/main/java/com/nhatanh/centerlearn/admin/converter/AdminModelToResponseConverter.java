package com.nhatanh.centerlearn.admin.converter;


import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.*;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.enums.RoomStatus;
import com.nhatanh.centerlearn.common.enums.TimeslotStatus;
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

    public AdminAccountResponse toAccountResponse(AccountModel model) {
        return AdminAccountResponse.builder()
            .id(model.getId())
            .username(model.getUsername())
            .displayName(model.getDisplayName())
            .email(model.getEmail())
            .phone(model.getPhone())
            .status(AccountStatus.fromString(model.getStatus()))
            .avatarId(model.getAvatarId())
            .createdAt(model.getCreatedAt())
            .updatedAt(model.getUpdatedAt())
            .build();
    }
}
