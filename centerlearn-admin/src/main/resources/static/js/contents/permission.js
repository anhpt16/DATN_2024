
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";
import permissionService from "../service/PermissionService.js";
import roleService from "../service/RoleService.js";
import permissonUI from "../ui/PermissionUI.js";

$(document).ready(function() {
    const roleSelect = $("#role-select");
    const statusSelect = $("#status-select");
    const methodSelect = $("#method-select");
    const permissionTbody = $("#permission-tbody");

    const roleDetailId = $("#role-detail-id");
    const roleDetailName = $("#role-detail-name");
    const roleDetailDisplayName = $("#role-detail-display-name");

    // Lấy thông tin chi tiết vai trò đã chọn
    roleSelect.on('change', async function() {
        let roleId = roleSelect.val();
        if (roleId == '') {
            permissonUI.renderRoleDetail(null);
        }
        try {
            const response = await roleService.getRoleById(roleId);
            permissonUI.renderRoleDetail(response);
        } catch (error) {
            console.log("Error: " + error);
        }
        fetchFilteredData();
    })

    // Reset table
    statusSelect.on('change', fetchFilteredData);
    methodSelect.on('change', fetchFilteredData);

    // Thêm quyền hạn
    permissionTbody.on('click', '.add-btn', async function() {
        const getRow = $(this).closest("tr");
        const id = roleSelect.val();
        const getFeatureUri = getRow.find(".permission-uri").text();
        const getFeatureMethod = getRow.find(".permission-method").text();
        let formData = {
            roleId: id,
            uriFeature: getFeatureUri,
            uriMethod: getFeatureMethod
        };
        try {
            const repsonse = await permissionService.addPermission(formData);
            showNotification('success', '', 'Thêm thành công');
            fetchFilteredData();
        } catch (error) {
            showNotification('error', 'Thêm thất bại', error.message);
        }
    })

    // Xóa quyền hạn
    permissionTbody.on('click', '.delete-btn', async function() {
        const getRow = $(this).closest("tr");
        const id = roleSelect.val();
        const roleName = roleSelect.find(`option[value="${id}"]`).text();
        const getFeatureUri = getRow.find(".permission-uri").text();
        const getFeatureMethod = getRow.find(".permission-method").text();
        let formData = {
            roleId: id,
            uriFeature: getFeatureUri,
            uriMethod: getFeatureMethod
        };
        const result = await showConfirmation('delete', 'Xác nhận xóa', `Vai trò: ${roleName}<br>Đường dẫn: ${formData.uriFeature}<br>Phương thức: ${formData.uriMethod}`);
        if (result) {
            try {
                const response = await permissionService.deletePermission(formData);
                showNotification('success', '', 'Xóa thành công');
                fetchFilteredData();
            } catch (error) {
                showNotification('error', 'Xóa thất bại', error.message);
            }
        }
    })

    // Lấy quyền hạn theo điều kiện lọc
    async function fetchFilteredData() {
        let queryString = '';
        let roleId = roleSelect.val();
        let status = statusSelect.val();
        let method = methodSelect.val();

        if (roleId) queryString += `&roleId=${roleId}`;
        if (status) queryString += `&status=${status}`;
        if (method) queryString += `&method=${method}`;

        try {
            const response = await permissionService.filterPermissionByType(queryString);
            permissonUI.renderTable(response);
        } catch (error) {
            console.log("Error: " + error);
        }
    }

})
