
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";
import roleService from "../service/RoleService.js";
import roleUI from "../ui/RoleUI.js";

$(document).ready(function() {
    const tbodyEl = $('#role-tbody');
    const refreshBtn = $(".refresh-btn");
    // Tạo vai trò
    const roleAddName = $("#role-add-name");
    const roleAddDisplayName = $("#role-add-display-name")
    const formAddRole = $("#form-add-role");
    const submitButton = formAddRole.find('button[type="submit"]');
    // Pagination
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");


    // Tạo vai trò
    formAddRole.on('submit', async function(event) {
        event.preventDefault();
        let formData = {
            name: roleAddName.val().trim(),
            displayName: roleAddDisplayName.val().trim(),
        }
        if (!formData.name || !formData.displayName) {
            showNotification('error', '', 'Phải điền đầy đủ thông tin !');
            return;
        }
        submitButton.prop('disabled', true);
        try {
            const response = await roleService.addRole(formData);
            showNotification('success', '', 'Tạo mới thành công');
            roleAddName.val('');
            roleAddDisplayName.val('');
        } catch(error) {
            showNotification('error', 'Tạo mới thất bại', `${error.message}`);
        } finally {
            submitButton.prop('disabled', false);
        }
    });

    // Sửa một vai trò
    tbodyEl.on('click', '.edit-btn', async function() {
        // Lấy dòng dữ liệu
        const getRoleRow = $(this).closest('tr');
        // Lấy thông tin hàng sửa
        const formData = {
            getRoleId: getRoleRow.find(".item-role-id").text(),
            getRoleName: getRoleRow.find(".item-role-name").text(),
            getRoleDisplayName: getRoleRow.find(".item-role-display-name").text(),
        }
        const result = await roleUI.renderEditModal(formData);
        if (result) {
            try {
                const formDataUpdate = {
                    displayName: result.displayName,
                }
                const response = await roleService.updateRole(formData.getRoleId, formDataUpdate);
                showNotification('success', '', 'Sửa thành công')
            } catch (error) {
                showNotification('error', '', 'Sửa thất bại')
            }
        }
    })

    // Xóa một vai trò
    tbodyEl.on('click', '.delete-btn', async function() {
        // Lấy dòng dữ liệu
        const getRoleRow = $(this).closest('tr');
        // Lấy thông tin hàng xóa
        const getRoleId = getRoleRow.find(".item-role-id").text();
        const getRoleName = getRoleRow.find(".item-role-name").text();
        const getRoleDisplayName = getRoleRow.find(".item-role-display-name").text();
        const result = await showConfirmation('delete', 'Xác nhận xóa', `Mã: ${getRoleId}<br>Tên: ${getRoleName}<br>Tên hiển thị: ${getRoleDisplayName}`);
        if (result) {
            try {
                const response = await roleService.deleteRole(getRoleId);
                showNotification('success', '', 'Xóa thành công');                
            } catch(error) {
                showNotification('error', 'Xóa thất bại', error.message);
            }
        }
    })

    // Chuyển trang
    function validatePageNumber(currentPage, totalPage) {
        nextPage.toggleClass('disabled-link', currentPage >= (totalPage - 1));
        prevPage.toggleClass('disabled-link', currentPage <= 0);
    }
    nextPage.on('click', function() {
        if (currentPage < totalPage - 1) {
            currentPage += 1;
        }            
        renderTable(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        renderTable(currentPage);
    })

    // Reset
    refreshBtn.on('click', function() {
        renderTable();
    })

    // Hiển thị bảng
    async function renderTable(page = 0, size = 10) {
        try {
            const response = await roleService.getRole(page, size);
            roleUI.renderTable(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
        } catch(error) {
            console.log("Error: " + error);
        }
    }

})