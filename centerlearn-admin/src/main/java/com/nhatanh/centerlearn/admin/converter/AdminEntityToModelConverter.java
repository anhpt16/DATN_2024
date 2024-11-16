package com.nhatanh.centerlearn.admin.converter;

import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.common.entity.*;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class AdminEntityToModelConverter {

    public TermModel toModel(Term term) {
        if (term == null) {
            return null;
        }
        return TermModel.builder()
            .id(term.getId())
            .name(term.getName())
            .slug(term.getSlug())
            .termType(term.getTermType())
            .parentId(term.getParentId())
            .description(term.getDescription())
            .build();
    }

    public RoomModel toModel(Room room) {
        if (room == null) {
            return null;
        }
        return RoomModel.builder()
            .id(room.getId())
            .name(room.getName())
            .slot(room.getSlot())
            .termId(room.getTermId())
            .description(room.getDescription())
            .status(room.getStatus())
            .createdAt(room.getCreatedAt())
            .updatedAt(room.getUpdatedAt())
            .build();
    }

    public TimeslotModel toModel(Timeslot timeslot) {
        if (timeslot == null) {
            return null;
        }
        return TimeslotModel.builder()
            .id(timeslot.getId())
            .period(timeslot.getPeriod())
            .timeStart(timeslot.getTimeStart())
            .timeEnd(timeslot.getTimeEnd())
            .description(timeslot.getDescription())
            .status(timeslot.getStatus())
            .createdAt(timeslot.getCreatedAt())
            .updatedAt(timeslot.getUpdatedAt())
            .build();
    }

    public RoleModel toModel(Role role) {
        if (role == null) {
            return null;
        }
        return RoleModel.builder()
            .id(role.getId())
            .name(role.getName())
            .displayName(role.getDisplayName())
            .createdAt(role.getCreatedAt())
            .build();
    }

    public UriModel toModel(Permission permission) {
        if (permission == null) {
            return null;
        }
        else {
            return UriModel.builder()
                .uriPath(permission.getFeatureUri())
                .uriMethod(MethodName.fromString(permission.getFeatureMethod()))
                .build();
        }
    }

    public PermissionModel toPermissionModel(Permission permission) {
        if (permission == null) {
            return null;
        }
        else {
            return PermissionModel.builder()
                .roleId(permission.getRoleId())
                .featureUri(permission.getFeatureUri())
                .featureMethod(permission.getFeatureMethod())
                .createdAt(permission.getCreatedAt())
                .build();
        }
    }

    public AccountModel toModel(Account account) {
        if (account == null) {
            return null;
        } else {
            return AccountModel.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .displayName(account.getDisplayName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .creatorId(account.getCreatorId())
                .avatarId(account.getAvatarImageId())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
        }
    }

    public AccountRoleModel toModel(AccountRole accountRole) {
        if (accountRole == null) {
            return null;
        } else {
            return AccountRoleModel.builder()
                .accountId(accountRole.getAccountId())
                .roleId(accountRole.getRoleId())
                .build();
        }
    }


}
