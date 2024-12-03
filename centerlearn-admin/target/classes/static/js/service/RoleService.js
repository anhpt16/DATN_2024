
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
        throw new Error(`Error while calling ${endpoint}: ${error.message}`);
    }
};

const roleService = {
    getRole: (page = 0, size = 10) => apiCall(`/roles?page=${page}&size=${size}`, "GET"),
    getRoleById: (roleId) => apiCall(`/roles/${roleId}`, "GET"),
    addRole: (formData) => apiCall(`/roles`, "POST", formData),
    deleteRole: (roleId) => apiCall(`/roles/${roleId}`, "DELETE"),
    updateRole: (roleId, formData) => apiCall(`/roles/${roleId}`, "PUT", formData),
}

export default roleService;