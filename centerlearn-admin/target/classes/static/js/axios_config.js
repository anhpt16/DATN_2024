
const axiosConfig = axios.create({
    baseURL: "http:/api/v1",
    headers: {
        'Content-Type': 'application/json',
    }
});

axiosConfig.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        const baseURL = config.baseURL || window.location.origin;
        console.log(baseURL)
        const url = new URL(config.url, baseURL);
        url.searchParams.set('lang', 'vi');
        config.url = url.pathname + url.search;
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

axiosConfig.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
)