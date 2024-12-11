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

        const response = await axiosMedia(config);
        console.log(url)
        if (response.status === 204) {
            return null;
        }
        return response.data;
    } catch (error) {
        console.error(`Error while calling ${endpoint}:`, error);
    }
};

const galleryService = {
    getGalleryFilter: async (queryString) => {
        if (queryString && !queryString.includes('?')) {
            queryString = '?' + queryString; 
        }
        return apiCall(`/media/image${queryString}`, "GET");
    },

    uploadImage: (formData) => apiCall(`/media/image`, "POST", formData),
    getImageDetailById: (id) => apiCall(`/media/image/${id}`, "GET"),
    updateImageById: (id, name) => apiCall(`/media/image/${id}`, "PUT", name),
    deleteImageById: (id) => apiCall(`/media/image/${id}`, "DELETE"),
};
export default galleryService;