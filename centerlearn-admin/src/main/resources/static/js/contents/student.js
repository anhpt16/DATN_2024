
import accountService from "../service/AccountService.js";
import studentUI from "../ui/StudentUI.js";
import { showNotification } from "../ui/notification.js";

$(document).ready(function() {
    // Add Account
    const formAddAccount = $("#form-add-user");
    const addAccountUsername = $("#add-account-username");
    const addAccountPassword = $("#add-account-password");
    const addAccountDisplayName = $("#add-account-display-name");
    const addAccountEmail = $("#add-account-email");
    const addAccountPhone = $("#add-account-phone");
    const addAccountRole = $("#add-account-role");
    const selectAccountStatus = $("#select-account-status");
    const selectAccountFrom = $("#select-account-from");
    const selectAccountTo = $("#select-account-to");
    const searchAccountCheckbox = $("#search-account-checkbox");
    const searchAccountContent = $("#search-account-content");
    const searchAccountType = $("#search-account-type");
    const searchAccountButton = $("#search-account-btn");

    // Chuyển trang
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const refreshBtn = $('#refresh-btn');
    // Thông báo lỗi
    const errorMessage = $("#error-message");
    const message = $("#error-message .error-content");

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
            const response = await accountService.addStudentAccount(formData);
            showNotification('success', '', 'Thêm thành công');
            addAccountUsername.val("");
            addAccountPassword.val("");
            addAccountDisplayName.val("");
            addAccountEmail.val("");
            addAccountPhone.val("");
        } catch (error) {
            showNotification('error', '', 'Thất bại');
            message.text(error.message);
            errorMessage.removeClass('d-none');
        }
    });

    errorMessage.on('click', '.close-icon i', function() {
        message.empty();
        errorMessage.addClass('d-none');
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
        if (dates.startDate !== null && dates.startDate !== undefined && dates.startDate !== '') {
            queryString += "&startDate=" + dates.startDate;
        }
        if (dates.endDate !== null && dates.endDate !== undefined && dates.endDate !== '') {
            queryString += "&endDate=" + dates.endDate;
        }
        console.log(queryString);
        try {
            const response = await accountService.getStudentAccountFilter(queryString);
            studentUI.renderTable(response.items);
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

    // Reset
    refreshBtn.on('click', function() {
        getAccountByFilter();
    })
})