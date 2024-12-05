const controllerService = async (endpoint, method = "GET", body = null) => {
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

        const response = await axiosControllerConfig(config);
        console.log(response)
        return response;
    } catch (error) {
        console.error(`Error while calling ${endpoint}:`, error);
        if (error.response && error.response.status === 401) {
            console.log("Redirecting to login page...");
            window.location.href = "/admin/login?lang=vi";
        }
        if (error.response && error.response.status === 403) {
            window.location.href = "/error/forbiden?lang=vi";
        }
        throw new Error(`Error while calling ${endpoint}: ${error.message}`);

    }
};


export default controllerService;