import util from "../utils.js";

const delegationUI = {
    el: {
        tbodyEl: $("#delegation-body"),
    },

    renderTable: (account) => {
        delegationUI.el.tbodyEl.empty();
        if (account === null) {
            delegationUI.el.tbodyEl.append('<tr><td colspan="8">No account available</td></tr>');
            return;
        }
        let row = '<tr class="fade-in" data-id="' + account.id + '">' +
            '<td class="item-account-id">' + account.id + '</td>' +
            '<td class="item-account-username">' + account.username + '</td>' +
            '<td class="item-account-display-name">' + account.displayName + '</td>' +
            '<td class="item-account-email">' + account.email + '</td>' +
            '<td class="item-account-phone">' + account.phone + '</td>' +
            '<td class="item-account-status" '
            + (account.status.colorCode ? 'style="color:' + account.status.colorCode + '"' : '') + '>' +
            account.status.displayName + '</td>' +
            '<td>' +
                '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
            '</td>' +
        '</tr>';
        delegationUI.el.tbodyEl.append(row);
        setTimeout(function() {
            delegationUI.el.tbodyEl.find('.fade-in').addClass('show');
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
                    <img src="https://via.placeholder.com/150" alt="Avatar" class="rounded-circle img-fluid">
                </div>
                <div class="row p-2" style="color: ${accountResponse.status.colorCode ? accountResponse.status.colorCode : ''}">
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

    renderAccountRoles: (rolesResponse) => {
        let container = $("#table-account-role");
        if (container.length === 0) {
            return;
        }
        container.empty();

        // Tạo phần tử thead và tbody một cách riêng biệt
        let thead = $('<thead>')
            .append(
                $('<tr>')
                    .append($('<th>').text('Mã'))
                    .append($('<th>').text('Tên'))
                    .append($('<th>').text('Tên hiển thị'))
                    .append($('<th>').text('Hành động'))
            );

        let tbody = $('<tbody>');

        // Lặp qua mảng rolesResponse để tạo các dòng <tr>
        rolesResponse.forEach(roleResponse => {
            let tr = $('<tr>').attr('data-id', roleResponse.id);
            let tdId = $('<td>').text(roleResponse.id);
            let tdName = $('<td>').text(roleResponse.name);
            let tdDisplayName = $('<td>').text(roleResponse.displayName);
            let tdAction = $('<td>').append('<i class="fa-solid fa-trash-can delete-btn"></i>');
            // Thêm các td vào tr
            tr.append(tdId, tdName, tdDisplayName, tdAction);
            // Thêm tr vào tbody
            tbody.append(tr);
        });
        container.html(thead).append(tbody);
    },

    renderMenuAddRoles: (notAssignedRolesResponse) => {
        let container = $("#menu-account-role");
        if (container.length === 0) {
            console.error("Không tìm thấy phần tử #menu-account-role");
            return;
        }
        container.empty();

        // Tạo form từ chuỗi HTML
        let form = $('<div>' + 
                    '<select name="" id="role-select" autocomplete="off"></select>' +
                    '<button class="btn-add-role">Thêm</button>' +
                    '</div>');
        // Duyệt qua mảng và thêm các option
        notAssignedRolesResponse.forEach(function(notAssignedRoleResponse) {
            let option = $('<option>').val(notAssignedRoleResponse.id)
                                    .text(notAssignedRoleResponse.id + ': ' + notAssignedRoleResponse.name);
            form.find('select').append(option);
        });

        container.append(form);
    },
}

export default delegationUI;