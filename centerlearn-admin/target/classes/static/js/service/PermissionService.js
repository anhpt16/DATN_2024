const apiCall = async (endpoint, method, body = null) => {
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


const permissionService = {
    filterPermissionByType: (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString;
        }
        return apiCall(`/permissions/by-type${queryString}`, "GET");
    },
    addPermission: (formData) => apiCall(`/permissions/add`, "POST", formData),
    deletePermission: (formData) => apiCall(`/permissions/delete`, "DELETE", formData),
}

export default permissionService;