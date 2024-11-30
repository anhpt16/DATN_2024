const apiUrl = "/api/v1";

const apiCall = async (endpoint, method = "GET", body = null) => {
    try {
        const options = {
            method,
            headers: { "Content-Type": "application/json" },
        };
        if (body) options.body = JSON.stringify(body);

        // Kiểm tra nếu endpoint có query string sẵn
        let url = `${apiUrl}${endpoint}`;
        if (url.includes('?')) {
            // Nếu đã có ?, nối thêm tham số lang=vi với &
            url += `&lang=vi`;
        } else {
            // Nếu chưa có ?, bắt đầu với ?
            url += `?lang=vi`;
        }

        const response = await fetch(url, options);
        console.log(url)
        if (response.ok) {
            if (response.status === 204) {
                return null;
            }
            return await response.json();
        }
        else {
            throw new Error(await response.text());
        }
    } catch (error) {
        console.error(`Error while calling ${endpoint}:`, error);
        throw new Error(`Error while calling ${endpoint}: ${error.message}`);
    }
};

const accountService = {
    getAccountFilter: async (queryString) => {
        // Đảm bảo đúng cách nối query string với ? và &.
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; // Thêm dấu ? nếu queryString không có
        }
        return apiCall(`/accounts${queryString}`, "GET");
    },

    getAccountById: (id) => apiCall(`/accounts/id/${id}`),
    getAccountByEmail: (email) => apiCall(`/accounts/email/${email}`),
    getAccountByPhone: (phone) => apiCall(`/accounts/phone/${phone}`),
    getAccountDetailById: (id) => apiCall(`/accounts/${id}`, "GET"),

    getAccountRolesByAccountId: (id) => apiCall(`/accounts/${id}/roles`, "GET"),
    getNotAssignedRolesByAccountId: (id) => apiCall(`/accounts/${id}/roles/not-assigned`, "GET"),

    addAccountRole: (accountId, roleId) => apiCall(`/accounts/${accountId}/roles/${roleId}`, "POST"),
    deleteAccountRole: (accountId, roleId) => apiCall(`/accounts/${accountId}/roles/${roleId}`, "DELETE")
};

export default accountService;