
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

const termService = {
    getTermByName: (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString;
        }
        return apiCall(`/terms/by-name${queryString}`, "GET");
    },
    addTerm: (formData) => apiCall(`/terms`, "POST", formData),
    getTermByTermType: (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString;
        }
        return apiCall(`/terms/by-term-type${queryString}`, "GET");
    },
    getTermById: (id) => apiCall(`/terms/${id}`, "GET"),
    updateTermById: (id, formData) => apiCall(`/terms/${id}`, "PUT", formData),
    getTypesByName: (name) => apiCall(`/terms/${name}/types`, "GET"),
    deleteTermById: (id) => apiCall(`/terms/${id}`, "DELETE"),
};

export default termService;