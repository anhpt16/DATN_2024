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

const lessonService = {
    getLessonSectionDetail: (lessonId, sectionId) => apiCall(`/lesson/${lessonId}/section/${sectionId}`, "GET"),
    getLessonExerciseDetail: (exerciseId) => apiCall(`/exercise/${exerciseId}`, "GET"),
    getExerciseStatuses: () => apiCall(`exercise/statuses`, "GET"),
    getSectionStatuses: () => apiCall(`section/statuses`, "GET"),
    getLessonStatuses: () => apiCall(`/lesson/statuses`, "GET"),
    getLessonById: (id) => apiCall(`/lesson/${id}`, "GET"),

    addSection: (lessonId, formData) => apiCall(`/lesson/${lessonId}/section`, "POST", formData),
    addExercise: (lessonId, formData) => apiCall(`/lesson/${lessonId}/exercise`, "POST", formData),
    deleteSection: (lessonId, sectionId) => apiCall(`/lesson/${lessonId}/section/${sectionId}`, "DELETE"),
    deleteExercise: (lessonId, exerciseId) => apiCall(`/lesson/${lessonId}/exercise/${exerciseId}`, "DELETE"),

    updateExerciseFromLesson: (lessonId, exerciseId, formData) => apiCall(`/lesson/${lessonId}/exercise/${exerciseId}`, "PUT", formData),
    updateSectionFromLesson: (lessonId, sectionId, formData) => apiCall(`/lesson/${lessonId}/section/${sectionId}`, "PUT", formData),
    updateLessonById: (id, formData) => apiCall(`/lesson/${id}`, "PUT", formData),

}

export default lessonService;