package com.nhatanh.centerlearn.admin.converter;


import com.nhatanh.centerlearn.admin.controller.service.TermServiceController;
import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.request.*;
import com.nhatanh.centerlearn.common.enums.*;
import com.nhatanh.centerlearn.common.model.AddLessonModel;
import com.nhatanh.centerlearn.common.request.AddLessonRequest;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.nhatanh.centerlearn.common.entity.PermissionId;
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
            .creatorId(resquest.getCreatorId())
            .build();
    }

    public AccountLoginModel toAccountLoginModel(AuthAccountRequest request) {
        return AccountLoginModel.builder()
            .username(request.getUsername())
            .password(base64Util.encodePassword(request.getPassword()))
            .build();
    }

    public AccountRoleModel toAccountRoleModel(long accountId, long roleId) {
        return AccountRoleModel.builder()
            .accountId(accountId)
            .roleId(roleId)
            .build();
    }

    public AddSubjectModel toAddSubjectModel(AddSubjectRequest request) {
        return AddSubjectModel.builder()
            .name(request.getName())
            .displayName(request.getDisplayName())
            .description(request.getDescription())
            .status(SubjectStatus.ACTIVE.name())
            .slug(SlugGenerate.createSlugWithId(request.getDisplayName()))
            .imageId(request.getImageId())
            .build();
    }

    public AddTextbookModel toAddTextbookModel(AddTextbookRequest request) {
        return AddTextbookModel.builder()
            .name(request.getName())
            .description(request.getDescription())
            .author(request.getAuthor())
            .url(request.getUrl())
            .status(TextbookStatus.ACTIVE.name())
            .slug(SlugGenerate.createSlugWithId(request.getName()))
            .subjectId(request.getSubjectId())
            .build();
    }

    public SaveSubjectModel toSaveSubjectModel(SaveSubjectRequest request, long subjectId) {
        return SaveSubjectModel.builder()
            .id(subjectId)
            .name(request.getName())
            .displayName(request.getDisplayName())
            .description(request.getDescription())
            .status(request.getStatus())
            .imageId(request.getImageId())
            .build();
    }

    public SaveTextbookModel toSaveTextbookModel(SaveTextbookRequest request, long textbookId) {
        return SaveTextbookModel.builder()
            .id(textbookId)
            .name(request.getName())
            .description(request.getDescription())
            .author(request.getAuthor())
            .url(request.getUrl())
            .status(request.getStatus())
            .build();
    }

    public AddLessonModel toAddLessonModel(AddLessonRequest request, long creatorId) {
        return AddLessonModel.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .creatorId(creatorId)
            .note(request.getNote())
            .priority(request.getPriority())
            .userTermId(request.getUserTermId())
            .build();
    }
}
