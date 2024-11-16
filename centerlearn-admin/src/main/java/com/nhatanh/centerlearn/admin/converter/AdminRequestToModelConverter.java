package com.nhatanh.centerlearn.admin.converter;


import com.nhatanh.centerlearn.admin.controller.service.TermServiceController;
import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.request.*;
import com.nhatanh.centerlearn.admin.validator.FormValidator;
import com.nhatanh.centerlearn.common.entity.PermissionId;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.enums.RoomStatus;
import com.nhatanh.centerlearn.common.enums.TimeslotStatus;
import com.nhatanh.centerlearn.common.utils.Base64Util;
import com.nhatanh.centerlearn.common.utils.SlugGenerate;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class AdminRequestToModelConverter {
    private final FormValidator formValidator;
    private final TermServiceController termServiceController;
    private final Base64Util base64Util;

    public SaveTermModel toSaveTermModelConverter(SaveTermRequest request) {
        long idParent = 0;
        // Decorate
        if (!formValidator.validateBlank(request.getParentName()) && !formValidator.validateBlank(request.getParentType())) {
            idParent = termServiceController.getIdByTermNameAndType(request.getParentName(), request.getParentType());
        }
        // Generate Slug
        String slug = SlugGenerate.createSlug(request.getName());
        // Converter
        return SaveTermModel.builder()
            .name(request.getName())
            .slug(slug)
            .termType(request.getTermType())
            .parentId(idParent)
            .description(request.getDescription())
            .build();
    }

    public SaveRoomModel toSaveRoomModelConverter(UpdateRoomResquest resquest) {
        return SaveRoomModel.builder()
            .name(resquest.getName())
            .slot(resquest.getSlot())
            .termId(resquest.getBase())
            .status(resquest.getStatus())
            .description(resquest.getDescription())
            .build();
    }

    public SaveRoomModel toSaveRoomModelConverter(SaveRoomRequest request) {
        return SaveRoomModel.builder()
            .name(request.getName())
            .description(request.getDescription())
            .slot(request.getSlot())
            .termId(request.getBase())
            .status(RoomStatus.ACTIVE.name())
            .build();
    }

    public SaveTimeslotModel toSaveTimeslotModelConverter(SaveTimeslotRequest request) {
        return SaveTimeslotModel.builder()
            .period(request.getPeriod())
            .timeStart(request.getTimeStart())
            .timeEnd(request.getTimeEnd())
            .description(request.getDescription())
            .status(TimeslotStatus.ACTIVE.name())
            .build();
    }

    public SaveTimeslotModel toSaveTimeslotModelConverter(UpdateTimeslotRequest request) {
        return SaveTimeslotModel.builder()
            .period(request.getPeriod())
            .timeStart(request.getTimeStart())
            .timeEnd(request.getTimeEnd())
            .description(request.getDescription())
            .status(request.getStatus())
            .build();
    }

    public SaveRoleModel toSaveRoleModelConverter(SaveRoleRequest request) {
        return SaveRoleModel.builder()
            .name(request.getName())
            .displayName(request.getDisplayName())
            .build();
    }

    public SaveRoleModel toSaveRoleModelConverter(UpdateRoleRequest request) {
        return SaveRoleModel.builder()
            .displayName(request.getDisplayName())
            .build();
    }

    public SavePermissionModel toSavePermissionModelConverter(SavePermissionRequest request) {
        return SavePermissionModel.builder()
            .roleId(request.getRoleId())
            .featureUri(request.getUriFeature())
            .featureMethod(request.getUriMethod())
            .build();
    }

    public PermissionId toPermissionId(SavePermissionRequest request) {
        PermissionId permissionId = new PermissionId();
        permissionId.setRoleId(request.getRoleId());
        permissionId.setFeatureUri(request.getUriFeature());
        permissionId.setFeatureMethod(request.getUriMethod());
        return permissionId;
    }

    public SaveAccountModel toSaveAccountModelConverter(SaveAccountResquest resquest) {
        return SaveAccountModel.builder()
            .username(resquest.getUsername())
            .password(resquest.getPassword())
            .displayName(resquest.getDisplayName())
            .email(resquest.getEmail())
            .phone(resquest.getPhoneNumber())
            .status(AccountStatus.ACTIVE.name())
            .roleId(resquest.getRoleId())
            .build();
    }

    public AccountLoginModel toAccountLoginModel(AuthAccountRequest request) {
        return AccountLoginModel.builder()
            .username(request.getUsername())
            .password(base64Util.encodePassword(request.getPassword()))
            .build();
    }
}
