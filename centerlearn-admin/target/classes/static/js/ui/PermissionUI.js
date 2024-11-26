
const permissonUI = {
    el: {
        permissionTbody: $("#permission-tbody"),
        roleSelect: $("#role-select"),
        statusSelect: $("#status-select"),
        methodSelect: $("#method-select"),
        roleDetailId: $("#role-detail-id"),
        roleDetailName: $("#role-detail-name"),
        roleDetailDisplayName: $("#role-detail-display-name"),
    },
    renderTable: (data) => {
        permissonUI.el.permissionTbody.empty();
        let roleId = permissonUI.el.roleSelect.val();
        let status = permissonUI.el.statusSelect.val();
        let method = permissonUI.el.methodSelect.val();
        // Đảm bảo rằng roleId và method được kiểm tra chính xác
        let isRoleIdInvalid = (roleId == null) || (roleId === "");
        let isMethodInvalid = (method == null) || (method === "");
        data.forEach(function(uriModel) {
            let row = $('<tr>').addClass('fade-in');
            // Điều kiện để disable checkbox và các nút
            let disableCheckbox = (uriModel.status === null || isRoleIdInvalid || isMethodInvalid);
            let disableAddBtn = (uriModel.status === null || uriModel.status.code === 2 || uriModel.status.code === 0 || isRoleIdInvalid);
            let disableDeleteBtn = (uriModel.status === null || uriModel.status.code === 1 || isRoleIdInvalid);
            // Tạo ô checkbox
            let checkbox = $('<input>')
                .attr('type', 'checkbox')
                .attr('autocomplete', 'off')
                .toggleClass('disabled-link', disableCheckbox);
            let checkboxCell = $('<td>').append(checkbox);
            // Tạo ô uriPath
            let uriPathCell = $('<td>')
              .addClass("permission-uri")
                .text(uriModel.uriPath)
                .css('color', uriModel.status && uriModel.status.colorCode ? uriModel.status.colorCode : '');
            // Tạo ô uriMethod
            let uriMethodCell = $('<td>')
              .addClass("permission-method")
                .text(uriModel.uriMethod.displayName)
                .css('color', uriModel.uriMethod.colorCode || '');
            // Tạo nút Add
            let addBtn = $('<i>')
                .addClass('fa-regular fa-square-plus add-btn')
                .toggleClass('disabled-action', disableAddBtn);
            // Tạo nút Delete
            let deleteBtn = $('<i>')
                .addClass('fa-solid fa-trash-can delete-btn')
                .toggleClass('disabled-action', disableDeleteBtn);
            let actionCell = $('<td>')
                .append(addBtn)
                .append(deleteBtn);
            // Thêm tất cả các ô vào hàng
            row.append(checkboxCell)
                .append(uriPathCell)
                .append(uriMethodCell)
                .append(actionCell);
            // Thêm hàng vào tbody
            permissonUI.el.permissionTbody.append(row);
            // Hiệu ứng fade-in
            setTimeout(function() {
                permissonUI.el.permissionTbody.find('.fade-in').addClass('show');
            }, 100);
        });
    },
    renderRoleDetail: (data) => {
        if (data) {
            permissonUI.el.roleDetailId.text(data.id || "N/A");
            permissonUI.el.roleDetailName.text(data.name || "N/A");
            permissonUI.el.roleDetailDisplayName.text(data.displayName || "N/A");
        } else {
            permissonUI.el.roleDetailId.text("N/A");
            permissonUI.el.roleDetailName.text("N/A");
            permissonUI.el.roleDetailDisplayName.text("N/A");
        }
    },

}

export default permissonUI;