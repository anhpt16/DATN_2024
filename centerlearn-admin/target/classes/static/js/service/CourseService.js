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

const courseService = {
    getCourseFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/admin/course${queryString}`, "GET");
    },
    getCourseById: (id) => apiCall(`/admin/course/${id}`, "GET"),
    getCourseTypes: () => apiCall(`/admin/course/types`, "GET"),
    getCourseStatus: () => apiCall(`/admin/course/statuses`, "GET"),
    addCourse: (formData) => apiCall(`/admin/course/`, "POST", formData),
    getCourseSubjectByCourseId: (id) => apiCall(`/admin/course/${id}/subjects/textbooks`, "GET"),
    updateCourse: (courseId, formData) => apiCall(`/admin/course/${courseId}`, "PUT", formData),
    addCourseSubject: (courseId, subjectId, textbookId) => apiCall(`/admin/course/${courseId}/subject/${subjectId}/textbook/${textbookId}`, "POST"),
    deleteCourseSubject: (courseId, subjectId) => apiCall(`/admin/course/${courseId}/subject/${subjectId}`, "DELETE"),
    updateManager: (courseId, managerId) => apiCall(`/admin/course/${courseId}/manager/${managerId}`, "PUT"),
}

export default courseService;
