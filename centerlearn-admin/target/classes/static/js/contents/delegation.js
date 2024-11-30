
import accountService from "../service/AccountService.js";
import delegationUI from "../ui/DelegationUI.js";
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";

$(document).ready(function() {
    const tbodyEl = $("#delegation-body");
    const editModal = $("#edit-modal");
    const searchAccountContent = $("#search-account-content");
    const searchAccountType = $("#search-account-type");
    const searchAccountButton = $("#search-account-btn");

    searchAccountButton.on('click', function() {
        tbodyEl.empty();
        getAccount();
    });
    searchAccountType.on('change', function() {
        const selectedType = $(this).val();
        if (selectedType === '1') {
            searchAccountContent.attr('type', 'number');
            searchAccountContent.attr('min', '1');
        }
        else {
            searchAccountContent.attr('type', 'text');
            searchAccountContent.removeAttr('min');
        }
    });

    tbodyEl.on('click', '.edit-btn', async function() {
        // Lấy dòng được chọn
        const getDelegationRow = $(this).closest('tr');
        // Lấy id
        const accountId = getDelegationRow.find(".item-account-id").text();
        delegationUI.renderEditModal();

        const results = await Promise.allSettled([
            accountService.getAccountDetailById(accountId),
            accountService.getAccountRolesByAccountId(accountId),
            accountService.getNotAssignedRolesByAccountId(accountId)
        ])
        const accountResponse = results[0];
        const rolesResponse = results[1];
        const notAssignedRolesResponse = results[2];
        if (accountResponse.status === 'fulfilled') {
            delegationUI.renderInfoDetail(accountResponse.value);
        } else {
            console.log('Error getting account details:', accountResponse.reason);
        }

        if (rolesResponse.status === 'fulfilled') {
            delegationUI.renderAccountRoles(rolesResponse.value);
        } else {
            console.log('Error getting roles:', rolesResponse.reason);
        }

        if (notAssignedRolesResponse.status === 'fulfilled') {
            delegationUI.renderMenuAddRoles(notAssignedRolesResponse.value);
        } else {
            console.log('Error getting not assigned roles:', notAssignedRolesResponse.reason);
        }
    })

    // Khi thêm vai trò
    editModal.on('click', '.btn-add-role', async function() {
        const accountId = $("#edit-modal").find('#id-info').attr('data-id');
        const roleId = $("#edit-modal").find('#role-select').val();
        console.log(accountId + " " + roleId)
        try {
            const response = await accountService.addAccountRole(accountId, roleId);
            showNotification('success', '', 'Thêm thành công');
            resetRender(accountId);
        } catch (error) {
            console.log("Error: " + error);
        }
    })

    // Khi xóa vai trò
    editModal.on('click', 'tbody .delete-btn', async function() {
        const accountId = $("#edit-modal").find('#id-info').attr('data-id');
        const getRow = $(this).closest("tr");
        const roleId = getRow.attr("data-id");
        try {
            const response = await accountService.deleteAccountRole(accountId, roleId);
            showNotification('success', '', 'Xóa thành công');
            resetRender(accountId);
        } catch (error) {
            console.log("Error: " + error);
        }
    })

    // Tìm tài khoản
    async function getAccount() {
        let accountContent = searchAccountContent.val();
        let checkAccountId = processAccountId(accountContent);
        
        if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '1' && checkAccountId === true) {
            try {
                const response = await accountService.getAccountById(accountContent);
                delegationUI.renderTable(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '2') {
            try {
                const response = await accountService.getAccountByEmail(accountContent);
                delegationUI.renderTable(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '3') {
            try {
                const response = await accountService.getAccountByPhone(accountContent);
                delegationUI.renderTable(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else {
            console.log("Account Type Invalid");
        }
    }


    function processAccountId(id) {
        return !isNaN(id) && Number(id) > 0;
    }

    async function resetRender(accountId) {
        const results = await Promise.allSettled([
            accountService.getAccountRolesByAccountId(accountId),
            accountService.getNotAssignedRolesByAccountId(accountId)
        ])
        const rolesResponse = results[0];
        const notAssignedRolesResponse = results[1];

        if (rolesResponse.status === 'fulfilled') {
            delegationUI.renderAccountRoles(rolesResponse.value);
        } else {
            console.log('Error getting roles:', rolesResponse.reason);
        }
        if (notAssignedRolesResponse.status === 'fulfilled') {
            delegationUI.renderMenuAddRoles(notAssignedRolesResponse.value);
        } else {
            console.log('Error getting not assigned roles:', notAssignedRolesResponse.reason);
        }
    }
})