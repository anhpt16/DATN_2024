
import accountService from "../service/AccountService.js";
import accountUI from "../ui/AccountUI.js";
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";


$(document).ready(function() {
    // Modal
    const editModal = $("#edit-modal");
    const tbodyEl = $("#table-account");
    // Add Account
    const formAddAccount = $("#form-add-user");
    const addAccountUsername = $("#add-account-username");
    const addAccountPassword = $("#add-account-password");
    const addAccountDisplayName = $("#add-account-display-name");
    const addAccountEmail = $("#add-account-email");
    const addAccountPhone = $("#add-account-phone");
    const addAccountRole = $("#add-account-role");
    
    const searchAccountCheckbox = $("#search-account-checkbox");
    const searchAccountContent = $("#search-account-content");
    const searchAccountType = $("#search-account-type");
    const searchAccountButton = $("#search-account-btn");
    const selectAccountStatus = $("#select-account-status");
    const selectAccountRole = $("#select-account-role");
    const selectAccountFrom = $("#select-account-from");
    const selectAccountTo = $("#select-account-to");

    // Chuyển trang
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const refreshBtn = $('#refresh-btn');
    // Thông báo lỗi
    const errorMessage = $("#error-message");
    const message = $("#error-message .error-content");
    // Modal


    // Thêm tài khoản
    formAddAccount.on('submit', async function(event) {
        event.preventDefault();
        let formData = {
            username: addAccountUsername.val(),
            password: addAccountPassword.val(),
            displayName: addAccountDisplayName.val(),
            email: addAccountEmail.val(),
            phoneNumber: addAccountPhone.val(),
            roleId: addAccountRole.val()
        };
        try {
            const response = await accountService.addAccount(formData);
            showNotification('success', '', 'Thêm thành công');
            addAccountUsername.val("");
            addAccountPassword.val("");
            addAccountDisplayName.val("");
            addAccountEmail.val("");
            addAccountPhone.val("");
        } catch (error) {
            showNotification('error', '', 'Thất bại');
            message.text(error.message);
            console.log(error)
            errorMessage.removeClass('d-none');
        }
    });

    errorMessage.on('click', '.close-icon i', function() {
        message.empty();
        errorMessage.addClass('d-none');
    })

    // Thông tin chi tiết tài khoản
    tbodyEl.on('click', '.view-btn', async function() {
        // Lấy dòng được chọn
        const getDelegationRow = $(this).closest('tr');
        // Lấy id
        const accountId = getDelegationRow.attr('data-id');
        console.log(accountId);
        accountUI.renderInfoModal();

        const results = await Promise.allSettled([
            accountService.getAccountDetailById(accountId),
        ])
        const accountResponse = results[0];
        if (accountResponse.status === 'fulfilled') {
            accountUI.renderInfoDetail(accountResponse.value);
        } else {
            console.log('Error getting account details:', accountResponse.reason);
        }
    })

    // Cập nhật tài khoản
    tbodyEl.on('click', '.edit-btn', async function() {
        // Lấy dòng được chọn
        const getDelegationRow = $(this).closest('tr');
        // Lấy id
        const accountId = getDelegationRow.attr('data-id');
        console.log(accountId);
        accountUI.renderEditModal();

        // Chi tiết tài khoản
        const results = await Promise.allSettled([
            accountService.getAccountDetailById(accountId),
        ])
        const accountResponse = results[0];
        if (accountResponse.status === 'fulfilled') {
            accountUI.renderInfoDetail(accountResponse.value);
        } else {
            console.log('Error getting account details:', accountResponse.reason);
        }
        // Trạng thái tài khoản
        try {
            const statusResponse = await accountService.getAccountStatuses();
            accountUI.renderStatuses(statusResponse);
        } catch (error) {
            console.log("Error " + error);
        }
    })

    // Thay đổi trạng thái
    editModal.on('change', '#account-status', async function() {
        const accountId = $("#id-info").attr('data-id');
        const currentStatus = $("#status-name").attr('data-name');
        const statusChange = $("#account-status").val();
        console.log(currentStatus);
        console.log(statusChange);
        if (currentStatus === statusChange) {
            return;
        }
        const result = await showConfirmation('update', 'Thay đổi trạng thái', statusChange);
        if (result) {
            try {
                const response = await accountService.updatedAccountStatus(accountId, statusChange);
                showNotification("success", '', 'Cập nhật thành công');
            } catch (error) {
                showNotification("error", '', 'Cập nhật thất bại');
                console.log("Error: " + error);
            }
            await resetAccountDetail(accountId);
            await resetAccountStatuses();
            getAccountByFilter(currentPage);
        }
    })

    // Đóng, mở tìm kiếm
    searchAccountCheckbox.change(function() {
        const isChecked = $(this).prop('checked');
        if (!$(this).prop('checked')) {
            searchAccountContent.prop('disabled', true);
            searchAccountType.prop('disabled', true);
            searchAccountButton.prop('disabled', true);
        } else {
            searchAccountContent.prop('disabled', false);
            searchAccountType.prop('disabled', false);
            searchAccountButton.prop('disabled', false);
        }
        
    })

    // Thay đổi điều kiện lọc
    searchAccountCheckbox.on('click', function() {
        if (!$(this).prop('checked')) {
            getAccountByFilter();
        }
    });
    selectAccountStatus.on('change', function() {
        getAccountByFilter();
    });
    selectAccountRole.on('change', function() {
        getAccountByFilter();
    });
    selectAccountFrom.on('change', function() {
        getAccountByFilter();
    });
    selectAccountTo.on('change', function() {
        getAccountByFilter();
    });
    searchAccountButton.on('click', function() {
        getAccountByFilter();
    });
    searchAccountType.on('change', function() {
        const selectedType = $(this).val();
        if (selectedType === '5') {
            searchAccountContent.attr('type', 'number');
            searchAccountContent.attr('min', '1');
        }
        else {
            searchAccountContent.attr('type', 'text');
            searchAccountContent.removeAttr('min');
        }
    });

    // Lấy danh sách tài khoản
    async function getAccountByFilter(page = 0, size = 10) {
        let queryString = '';
        queryString += "&page=" + page;
        queryString += "&size=" + size;

        let accountContent = searchAccountContent.val();
        let accountStatus = selectAccountStatus.val();
        let accountRole = selectAccountRole.val();
        let accountDateStart = selectAccountFrom.val();
        let accountDateEnd = selectAccountTo.val();
        // Kiểm tra ngày tháng hợp lệ
        let dates = processDates(accountDateStart, accountDateEnd);
        // Kiểm tra mã tài khoản hợp lệ
        let checkAccountId = processAccountId(accountContent);

        if (searchAccountCheckbox.prop('checked')) {
            console.log(searchAccountType.val());
            console.log(accountContent);
            console.log(checkAccountId);
            if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '1') {
                queryString += "&username=" + accountContent;
            }
            else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '2') {
                queryString += "&displayName=" + accountContent;
            }
            else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '3') {
                queryString += "&email=" + accountContent;
            }
            else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '4') {
                queryString += "&phone=" + accountContent;
            }
            else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '5' && checkAccountId === true) {
                queryString += "&id=" + accountContent;
            }
            else {
                console.log("Account Type Invalid");
            }
        }
        if (accountStatus !== null && accountStatus !== undefined && accountStatus !== '') {
            queryString += "&status=" + accountStatus;
        }
        if (accountRole !== null && accountRole !== undefined && accountRole !== '') {
            queryString += "&roleId=" + accountRole;
        }
        if (dates.startDate !== null && dates.startDate !== undefined && dates.startDate !== '') {
            queryString += "&startDate=" + dates.startDate;
        }
        if (dates.endDate !== null && dates.endDate !== undefined && dates.endDate !== '') {
            queryString += "&endDate=" + dates.endDate;
        }
        console.log(queryString);
        try {
            const response = await accountService.getAccountFilter(queryString);
            accountUI.renderTable(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
        } catch (error) {
            console.log("Error: " + error);
        }

    }

    function processDates(startDate, endDate) {
        // Hàm kiểm tra tính hợp lệ của ngày tháng
        console.log("start: " + startDate + "|| end: " + endDate);
        function isValidDate(dateString) {
          const regex = /^\d{4}-\d{2}-\d{2}$/;
          return dateString && regex.test(dateString) && !isNaN(new Date(dateString).getTime());
        }
        // Kiểm tra tính hợp lệ của từng trường ngày
        const validStartDate = isValidDate(startDate) ? startDate : null;
        const validEndDate = isValidDate(endDate) ? endDate : null;
  
        // Xóa class lỗi trước đó nếu có
        selectAccountFrom.removeClass('invalid');
        selectAccountTo.removeClass('invalid');
  
        // Nếu cả hai trường đều không có giá trị hợp lệ, trả về null
        if (!validStartDate && !validEndDate) {
          if (startDate) {
              selectAccountFrom.addClass('invalid');
          }
          if (endDate) {
              selectAccountTo.addClass('invalid');
          }
          return { startDate: null, endDate: null };
        }
        // Nếu cả hai ngày đều hợp lệ, kiểm tra ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc
        if (validStartDate && validEndDate && new Date(validStartDate) > new Date(validEndDate)) {
          selectAccountFrom.addClass('invalid');
          selectAccountTo.addClass('invalid');
          return { startDate: null, endDate: null };
        }
        // Trả về kết quả sau khi đã xử lý
        return { startDate: validStartDate, endDate: validEndDate };
    }
    function processAccountId(id) {
        return !isNaN(id) && Number(id) > 0;
    }



    // Reset
    refreshBtn.on('click', function() {
        getAccountByFilter(currentPage);
    })


    // Reset Account Detail
    async function resetAccountDetail(accountId) {
        try {
            const accountResponse = await accountService.getAccountDetailById(accountId);
            accountUI.renderInfoDetail(accountResponse);
        } catch (error) {
            console.log("Error: " + error);
        }   
    }
    // Reset Account Statuses
    async function resetAccountStatuses() {
        try {
            const statuses = await accountService.getAccountStatuses();
            accountUI.renderStatuses(statuses);
        } catch (error) {
            console.log("Error: " + error);
        }
    }


    // Chuyển trang
    function validatePageNumber(currentPage, totalPage) {
        nextPage.toggleClass('disabled-link', currentPage >= (totalPage - 1));
        prevPage.toggleClass('disabled-link', currentPage <= 0);
    }
    nextPage.on('click', function() {
        if (currentPage < totalPage - 1) {
            currentPage += 1;
        }            
        getAccountByFilter(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        getAccountByFilter(currentPage);
    })
    
})

