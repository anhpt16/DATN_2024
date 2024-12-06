const apiCall = async (endpoint, method = "GET", body = null) => {
    try {
        let url = `${endpoint}`;
        const config = {
            method,
            url,
            data: body,
        }

        if (body) {
            config.data = body;
        }

        const response = await axiosConfig(config);
        console.log(url)
        if (response.status === 204) {
            return null;
        }
        return response.data;
    } catch (error) {
        console.error(`Error while calling ${endpoint}:`, error);

        // Kiểm tra nếu có lỗi từ server
        if (error.response) {
            if (error.response.status === 400) {
                // Nếu lỗi là HttpBadRequestException
                const errorData = error.response.data; // Lấy lỗi từ body response
                const errorMessage = Object.entries(errorData)
                    .map(([key, value]) => `${key}: ${value}`)
                    .join(' | ');
                throw new Error(`${errorMessage}`);
            }
            // Bạn có thể xử lý thêm các mã lỗi khác ở đây (500, 401...)
            else {
                throw new Error(`Unexpected error: ${error.message}`);
            }
        } else {
            // Trường hợp không có response (network error, timeout...)
            throw new Error(`Network error: ${error.message}`);
        }
    }
};

const accountService = {
    getAccountFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/accounts${queryString}`, "GET");
    },
    getStudentAccountFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString;
        }
        return apiCall(`/accounts/students${queryString}`, "GET");
    },
    getTeacherAccountFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString;
        }
        return apiCall(`/accounts/teachers${queryString}`, "GET");
    },

    getAccountById: (id) => apiCall(`/accounts/id/${id}`),
    getAccountByEmail: (email) => apiCall(`/accounts/email/${email}`),
    getAccountByPhone: (phone) => apiCall(`/accounts/phone/${phone}`),
    getAccountDetailById: (id) => apiCall(`/accounts/${id}`, "GET"),

    getAccountRolesByAccountId: (id) => apiCall(`/accounts/${id}/roles`, "GET"),
    getNotAssignedRolesByAccountId: (id) => apiCall(`/accounts/${id}/roles/not-assigned`, "GET"),

    addAccountRole: (accountId, roleId) => apiCall(`/accounts/${accountId}/roles/${roleId}`, "POST"),
    deleteAccountRole: (accountId, roleId) => apiCall(`/accounts/${accountId}/roles/${roleId}`, "DELETE"),

    addAccount: (formData) => apiCall(`/accounts/add`, "POST", formData),
    addStudentAccount: (formData) => apiCall(`/accounts/add-student`, "POST", formData),
    addTeacherAccount: (formData) => apiCall(`/accounts/add-teacher`, "POST", formData),

    getAccountStatuses: () => apiCall(`/accounts/statuses`, "GET"),
    updatedAccountStatus: (accountId, statusName) => apiCall(`/accounts/${accountId}/status/${statusName}`, "PUT"),
    getUserByToken: () => apiCall(`/admin/login/user`, "GET"),
};

export default accountService;