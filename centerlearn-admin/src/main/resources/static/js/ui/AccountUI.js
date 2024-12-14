
import util from "../utils.js";

const accountUI = {
    el: {
        tbodyEl: $("#table-account"),
    },
    renderTable: (accounts) => {
        accountUI.el.tbodyEl.empty();
        if (accounts.length === 0) {
            accountUI.el.tbodyEl.append('<tr><td colspan="8">No account available</td></tr>');
            return;
        }
        accounts.forEach(function(account) {
            let row = '<tr class="fade-in" data-id="' + account.id + '">' +
                '<td class="item-account-image"><img src="'+ (account.avatarUrl && account.avatarUrl != '' ? account.avatarUrl : '/images/user_image_default.jpg') +'" alt="Avatar"></td>' +
                '<td class="item-account-username">' + account.username + '</td>' +
                '<td class="item-account-display-name">' + account.displayName + '</td>' +
                '<td class="item-account-email">' + account.email + '</td>' +
                '<td class="item-account-phone">' + account.phone + '</td>' +
                '<td class="item-account-status" '
                + (account.status.colorCode ? 'style="color:' + account.status.colorCode + '"' : '') + '>' +
                account.status.displayName + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-eye view-btn"></i>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                '</td>' +
            '</tr>';
            accountUI.el.tbodyEl.append(row);
        })
        setTimeout(function() {
            accountUI.el.tbodyEl.find('.fade-in').addClass('show');
          }, 100);
    },

    renderEditModal: () => {
        let container = $("#edit-modal");
        if (container.length === 0) {
            container = $('<div id="edit-modal"></div>').appendTo('body');
        }
        let modal = $(`
            <div class="modal fade" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header text-center d-flex align-items-center justify-content-between">
                            <h5 class="modal-title">Thông tin chi tiết</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="info-container">
                                <div id="account-info" class="row account-info">            
                                </div>
                                <hr>
                                <div class="d-flex align-items-center">
                                    <div class="me-2">Thay đổi trạng thái: </div>
                                    <select id="account-status">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        `)
        container.html(modal);
        const modalInstance = new bootstrap.Modal(modal[0]);
        modalInstance.show();
        // Lắng nghe sự kiện khi đóng confirmation
        modal.on('hidden.bs.modal', function () {
            container.empty(); // Xóa tất cả nội dung trong container
        });
    },

    

    renderInfoModal: () => {
        let container = $("#info-modal");
        if (container.length === 0) {
            container = $('<div id="info-modal"></div>').appendTo('body');
        }
        let modal = $(`
            <div class="modal fade" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header text-center d-flex align-items-center justify-content-between">
                            <h5 class="modal-title">Thông tin chi tiết</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="info-container">
                                <div id="account-info" class="row account-info">            
                                </div>
                                <hr>
                                <div class="account-role-container">
                                    <div id="menu-account-role" class="menu-account-role d-flex align-items-center justify-content-center">                       
                                    </div>
                                    <table id="table-account-role" class="table-account-role">
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        `)
        container.html(modal);
        const modalInstance = new bootstrap.Modal(modal[0]);
        modalInstance.show();
        // Lắng nghe sự kiện khi đóng confirmation
        modal.on('hidden.bs.modal', function () {
            container.empty(); // Xóa tất cả nội dung trong container
        });
    },

    renderInfoDetail: (accountResponse) => {
        let container = $("#account-info");
        if (container.length === 0) {
            return;
        }
        let form = $(`
            <div class="col-lg-4 d-flex flex-column align-items-center justify-content-center">
                <div class="row p-2">
                    <strong id="id-info" data-id="${accountResponse.id || ''}">#${accountResponse.id}</strong>
                </div>
                <div class="row p-2">
                    <img class="avatar-info" src="${accountResponse.avatarUrl != null && accountResponse.avatarUrl != '' ? accountResponse.avatarUrl : '/images/user_image_default.jpg'}">
                </div>
                <div id="status-name" data-name="${accountResponse.status.name || ''}" class="row p-2" style="color: ${accountResponse.status.colorCode ? accountResponse.status.colorCode : ''}">
                    ${accountResponse.status.displayName}
                </div>
            </div>
            <div class="col-lg-8 table-info-container">
                <table>
                    <tr>
                        <th>Tên hiển thị: </th>
                        <td>${accountResponse.displayName}</td>
                    </tr>
                    <tr>
                        <th>Tài khoản: </th>
                        <td>${accountResponse.username}</td>
                    </tr>
                    <tr>
                        <th>Email: </th>
                        <td>${accountResponse.email}</td>
                    </tr>
                    <tr>
                        <th>Số điện thoại: </th>
                        <td>${accountResponse.phone}</td>
                    </tr>
                    <tr>
                        <th>Ngày tạo: </th>
                        <td>${util.formatDateTime(accountResponse.createdAt)}</td>
                    </tr>
                    <tr>
                        <th>Ngày cập nhật: </th>
                        <td>${util.formatDateTime(accountResponse.updatedAt)}</td>
                    </tr>
                    <tr>
                        <th>Người tạo: </th>
                        <td>${accountResponse.creatorId}: ${accountResponse.creatorName}</td>
                    </tr>
                </table>
            </div>    
        `)
        container.html(form);
    },

    renderStatuses: (statuses) => {
        let container = $("#account-status");
        container.empty();
        let currentStatus = $("#status-name").attr('data-name');
        console.log("render: " + currentStatus);
        if (container.length === 0) {
            return;
        }
        statuses.forEach(status => {
            let option = $(`
                <option style="color: ${status.colorCode || ''}" value="${status.name}" ${status.name === currentStatus ? ' selected' : ''}>
                    ${status.displayName}
                    ${status.name === currentStatus ? '✔' : ''}
                </option>
                `)
            container.append(option);
        })
    },
}

export default accountUI;