
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
        console.log(response)
        return response;
    } catch (error) {
        if (error.response) {
            if (error.response.data) {
                throw new Error(`${error.response.data}`);
            }
            throw new Error(`Error while calling ${endpoint}: ${error.message}`);
        }
    }
};


const loginService = {
    login: (formData) => apiCall(`/admin/login`, "POST", formData),
}

export default loginService;