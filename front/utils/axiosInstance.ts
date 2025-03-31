import axios from "axios";

// Default to local development URL if environment variable is not set
const BASE_URL = "http://localhost:9090";

export const AxiosInstance = axios.create({
    baseURL: BASE_URL
});

// Add a request interceptor for debugging
AxiosInstance.interceptors.request.use(
    config => {
        console.log(`Making ${config.method?.toUpperCase()} request to: ${config.baseURL}${config.url}`);
        return config;
    },
    error => {
        console.error("Request error:", error);
        return Promise.reject(error);
    }
);