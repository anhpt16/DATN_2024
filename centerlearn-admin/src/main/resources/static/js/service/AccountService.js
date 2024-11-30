const apiUrl = "/api/v1";

const apiCall = async (endpoint, method = "GET", body = null) => {
    try {
        const options = {
            method,
            headers: { "Content-Type": "application/json" },
        };
        if (body) options.body = JSON.stringify(body);

        const response = await fetch(`${apiUrl}${endpoint}?lang=vi`, options);

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
        throw new Error("Error: " + error);
    }
};

const accountService = {
    getAccountFilter: async (queryString) => {
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