
const axiosConfig = axios.create({
    baseURL: "http:/api/v1",
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true,
});
const axiosControllerConfig = axios.create({
    withCredentials: true,
})


axiosConfig.interceptors.request.use(
    (config) => {
        const baseURL = config.baseURL || window.location.origin;
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

axiosControllerConfig.interceptors.request.use(
    (config) => {
        // Kiểm tra nếu URL chưa có tham số 'lang'
        const url = new URL(config.url, window.location.origin);
        if (!url.searchParams.has('lang')) {
            url.searchParams.append('lang', 'vi');
        }
        // Cập nhật lại URL trong config
        config.url = url.toString();

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)

axiosControllerConfig.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            window.location.href = "/admin/login?lang=vi";
        }
        return Promise.reject(error);
    }
)


