package com.nhatanh.centerlearn.common.validator;

import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.service.AccountRoleService;
import com.nhatanh.centerlearn.common.service.AccountService;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.common.service.RoleService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EzySingleton
@AllArgsConstructor
public class AccountValidator {
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final RoleService roleService;
    private final MediaService mediaService;
    private final FormValidator formValidator;

    public void validate(long userId, long roleId) {
        this.accountService.getAccountById(userId);
        this.roleService.getRoleByRoleId(roleId);
        this.accountRoleService.getAccountRoleByAccountIdAndRoleId(userId, roleId);
    }

    public void validateAccountAvatar(long accountId, long avatarId) {
        Map<String, String> errors = new HashMap<>();
        AccountModel accountModel = this.accountService.getAccountById(accountId);
        if (accountModel == null) {
            errors.put("account", "invalid");
        }
        GalleryModel galleryModel = this.mediaService.getGalleryByAccountIdAndId(accountId, avatarId);
        if (galleryModel == null) {
            errors.put("User don't have image with id: " + avatarId, "");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateEmail(String email) {
        List<String> errors = new ArrayList<>();
        if (formValidator.validateBlank(email)) {
            errors.add("Email blank");
        }
        if (!formValidator.validateEmail(email)) {
            errors.add("Email Invalid");
        }
        if (this.accountService.getAccountByEmail(email) > 0) {
            errors.add("Email Exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
    public void validatePhone(String phone) {
        List<String> errors = new ArrayList<>();
        if (formValidator.validateBlank(phone)) {
            errors.add("Phone blank");
        }
        if (!formValidator.validatePhoneNumber(phone)) {
            errors.add("Phone Invalid");
        }
        if (this.accountService.getAccountByPhone(phone) > 0) {
            errors.add("Phone Exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
    public void validateDisplayName(String displayName) {
        List<String> errors = new ArrayList<>();
        if (formValidator.validateBlank(displayName)) {
            errors.add("DisplayName blank");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
