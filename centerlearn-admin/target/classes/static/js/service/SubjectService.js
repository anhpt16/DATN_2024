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

const subjectService = {
    getSubjectFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/subject${queryString}`, "GET");
    },
    addSubjectTextbook: async (subjectId, queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/subject/${subjectId}/textbook${queryString}`, "POST");
    },
    deleteSubjectTextbook: async (subjectId, queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/subject/${subjectId}/textbook${queryString}`, "DELETE");
    },

    getTextbooksBySubjectId: (subjectId) => apiCall(`/subject/${subjectId}/textbook`, "GET"),
    getSubjectDetailById: (id) => apiCall(`/subject/${id}`, "GET"),
    getSubjectStatuses: () => apiCall(`/subject/statuses`, "GET"),
    addSubject: (formData) => apiCall(`/subject`, "POST", formData),
    updateSubject: (id, formData) => apiCall(`/subject/${id}`, "PUT", formData),
    getAllSubjectShort: () => apiCall(`/subject/all-short`, "GET"),
};

export default subjectService;