package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.model.AccountRoleModel;
import com.nhatanh.centerlearn.admin.model.RoleModel;
import com.nhatanh.centerlearn.admin.request.SaveAccountResquest;
import com.nhatanh.centerlearn.admin.service.AccountRoleService;
import com.nhatanh.centerlearn.admin.service.AccountService;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@EzySingleton
public class AccountValidator {
    private final FormValidator formValidator;
    private final RoleService roleService;
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;

    public void validate(SaveAccountResquest resquest) {
        Map<String, String> errors = new HashMap<>();
        // Check blank
        if (formValidator.validateBlank(resquest.getUsername())) {
            errors.put("Usename", "Blank");
        }
        if (formValidator.validateBlank(resquest.getPassword())) {
            errors.put("Password", "Blank");
        }
        if (formValidator.validateBlank(resquest.getDisplayName())) {
            errors.put("DisplayName", "Blank");
        }
        if (formValidator.validateBlank(resquest.getEmail())) {
            errors.put("Email", "Blank");
        }
        if (formValidator.validateBlank(resquest.getPhoneNumber())) {
            errors.put("PhoneNumber", "Blank");
        }
        if (!formValidator.validateId(resquest.getRoleId())) {
            errors.put("RoleId", "Invalid");
        }
        // Check invalid
        if (!formValidator.validateSpecialCharacter(resquest.getUsername())) {
            errors.put("UseName", "has special character");
        }
        if (!formValidator.validateSpecialCharacter(resquest.getDisplayName())) {
            errors.put("DisplayName", "has special character");
        }
        if (!formValidator.validateEmail(resquest.getEmail())) {
            errors.put("Email", "invalid");
        }
        if (!formValidator.validatePhoneNumber(resquest.getPhoneNumber())) {
            errors.put("PhoneNumber", "invalid");
        }
        if (this.roleService.getRoleById(resquest.getRoleId()) == null) {
            errors.put("Role", "invalid");
        }
        if (this.accountService.getAccountIdByUsername(resquest.getUsername()) > 0) {
            errors.put("Username", "exist");
        }
        if (this.accountService.getAccountIdByEmail(resquest.getEmail()) > 0) {
            errors.put("Email", "exist");
        }
        if (this.accountService.getAccountIdByPhone(resquest.getPhoneNumber()) > 0) {
            errors.put("PhoneNumber", "exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateCriteriaFilter(AccountFilterCriteria criteria) {
        Map<String, String> errors = new HashMap<>();

        if (criteria.getRoleId() > 0) {
            if(this.roleService.getRoleById(criteria.getRoleId()) == null) {
                errors.put("RoleId", "is not exist");
            }
        }
        boolean statusExist = criteria.getStatus() == 0 || this.accountService.getAllAccountStatus().stream()
            .anyMatch(status -> status.getCode() == criteria.getStatus());
        if(!statusExist) {
            errors.put("Status", "is not exist");
        }

        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateAddAccountRole(long accountId, long roleId) {
        Map<String, String> errors = new HashMap<>();
        AccountModel accountModel = this.accountService.getAccountById(accountId);
        if (accountModel == null) {
            errors.put("Account with id: " + accountId, " invalid");
        }
        RoleModel roleModel = this.roleService.getRoleById(roleId);
        if (roleModel == null) {
            errors.put("Role with id: " + roleId, " invalid");
        }
        AccountRoleModel accountRoleModel = this.accountRoleService.getAccountRole(accountId, roleId);
        if (accountRoleModel != null) {
            errors.put("AccountId :" + accountId + " with RoleId: " + roleId, " exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateDeleteAccountRole(long accountId, long roleId) {
        Map<String, String> errors = new HashMap<>();
        AccountModel accountModel = this.accountService.getAccountById(accountId);
        if (accountModel == null) {
            errors.put("Account with id: " + accountId, " invalid");
        }
        RoleModel roleModel = this.roleService.getRoleById(roleId);
        if (roleModel == null) {
            errors.put("Role with id: " + roleId, " invalid");
        }
        AccountRoleModel accountRoleModel = this.accountRoleService.getAccountRole(accountId, roleId);
        if (accountRoleModel == null) {
            errors.put("AccountId :" + accountId + " with RoleId: " + roleId, " not exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
