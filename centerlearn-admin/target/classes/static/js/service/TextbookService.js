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

const textbookService = {
    getTextbookFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/textbook${queryString}`, "GET");
    },

    addTextbook: (formData) => apiCall(`/textbook`, "POST", formData),
    getTextbookDetailById: (id) => apiCall(`/textbook/${id}`, "GET"),
    getTextbookStatus: () => apiCall(`/textbook/statuses`, "GET"),
    updateTextbookById: (id, formData) => apiCall(`/textbook/${id}`, "PUT", formData),
}

export default textbookService;