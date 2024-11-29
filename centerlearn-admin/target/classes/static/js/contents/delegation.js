
import accountService from "../service/AccountService.js";
import delegationUI from "../ui/DelegationUI.js";
import { showNotification } from "../ui/notification.js";

$(document).ready(function() {
    const tbodyEl = $("#delegation-body");
    const searchAccountContent = $("#search-account-content");
    const searchAccountType = $("#search-account-type");
    const searchAccountButton = $("#search-account-btn");

    searchAccountButton.on('click', getAccount);
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
        const accountId = getDelegationRow.find(".item-role-id").text();
        // Lấy thông tin chi tiết tài khoản

        // Lấy thông tin các vai trò của tài khoản

        // Hiển thị thông tin chi tiết tài khoản

        // Hiển thị các vai trò tài khoản
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
})