
const roleUI = {
    el: {
        tbodyEl: $('#role-tbody'),
    },
    formatDateTime: (dateTime) => {
        // Tách phần date và time
        let parts = dateTime.split('T');
        // Loại bỏ phần mili giây nếu có (giữ lại giờ, phút, giây)
        let timeWithoutMilliseconds = parts[1].split(':').slice(0, 3).join(':');
        // Gộp lại phần ngày và giờ với dấu cách thay cho 'T'
        let formattedDateTime = parts[0] + ' ' + timeWithoutMilliseconds;
        return formattedDateTime;
    },
    renderTable: (roles) => {
        roleUI.el.tbodyEl.empty();
        if (roles.length === 0) {
            roleUI.el.tbodyEl.append('<tr><td colspan="8">No roles available</td></tr>');
            return;
        }
        roles.forEach(function(role) {
            let row = '<tr class="fade-in" data-id="' + role.id + '">' +
                '<td class="item-role-id">' + role.id + '</td>' +
                '<td class="item-role-name">' + role.name + '</td>' +
                '<td class="item-role-display-name">' + role.displayName + '</td>' +
                '<td class="item-role-created">' + roleUI.formatDateTime(role.createdAt) + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                    '<i class="fa-solid fa-trash-can delete-btn"></i>' +
                '</td>' +
            '</tr>';
            roleUI.el.tbodyEl.append(row);
        });
        setTimeout(function() {
            roleUI.el.tbodyEl.find('.fade-in').addClass('show');
        }, 100);
    },
    renderEditModal: (formData) => {
        return new Promise((resolve, reject) => {
            let container = $('#edit-modal');
            if (container.length === 0) {
                container = $('<div id="edit-modal"></div>').appendTo('body');
            }
            let form = $(`
                <div class="modal fade" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <i class="fa-solid fa-user-pen text-warning"></i>
                            <h5 class="modal-title text-warning ms-3">Chỉnh sửa vai trò</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="edit-role-form">
                                <div class="mb-3">
                                    <label for="edit-role-id" class="form-label">Mã</label>
                                    <input type="text" id="edit-role-id" name="id" class="form-control" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="edit-role-name" class="form-label">Tên vai trò</label>
                                    <input type="text" id="edit-role-name" name="name" class="form-control" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="edit-role-display-name" class="form-label">Tên hiển thị</label>
                                    <input type="text" id="edit-role-display-name" name="display-name" class="form-control">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="button" class="btn btn-primary confirm-action-btn">Xác nhận</button>
                        </div>
                        </div>
                    </div>
                </div>
            `)
            container.html(form);
            // Điền giá trị
            const roleId = $('#edit-role-id');
            const roleName = $('#edit-role-name');
            const roleDisplayName = $('#edit-role-display-name')
            roleId.val(formData.getRoleId);
            roleName.val(formData.getRoleName);
            roleDisplayName.val(formData.getRoleDisplayName);

            const modalInstance = new bootstrap.Modal(form[0]);
            modalInstance.show();
            // Lắng nghe sự kiện khi đóng confirmation
            form.on('hidden.bs.modal', function () {
                container.empty(); // Xóa tất cả nội dung trong container
                resolve(null);
            });
            form.find('.confirm-action-btn').on('click', () => {
                const updatedFormData = {
                    id: roleId.val(),
                    name: roleName.val(),
                    displayName: roleDisplayName.val().trim(),
                }
                container.empty();
                modalInstance.hide();
                resolve(updatedFormData);
            })
        })
    },
}

export default roleUI;