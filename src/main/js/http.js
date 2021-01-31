import axios from "axios";

const http = (baseUrl = "http://localhost:3000/api/") => {
    const options = {
        baseURL: baseUrl,
        xsrfCookieName: "XSRF-TOKEN",
        xsrfHeaderName: "X-XSRF-TOKEN",
    };

    const client = axios.create(options);

    // Add a request interceptor
    client.interceptors.request.use(
        (requestConfig) => requestConfig,
        (requestError) => {
            return Promise.reject(requestError);
        }
    );

    // Add a response interceptor
    client.interceptors.response.use(
        (response) => response,
        (error) => {
            if (error.response.status >= 500) {
            }

            return Promise.reject(error);
        }
    );

    return client;
};

class ApiClient {
    constructor(baseUrl = null) {
        this.client = http(baseUrl);
    }

    get(url, conf = {}) {
        return this.client
            .get(url, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }

    delete(url, conf = {}) {
        return this.client
            .delete(url, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }

    head(url, conf = {}) {
        return this.client
            .head(url, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }

    options(url, conf = {}) {
        return this.client
            .options(url, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }

    /**
     * @param {string} url
     * @param {Object} data
     * @param {AxiosRequestConfig} conf
     * @returns {Promise<AxiosResponse<any>>}
     */
    post(url, data = {}, conf = {}) {
        return this.client
            .post(url, data, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }

    put(url, data = {}, conf = {}) {
        return this.client
            .put(url, data, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }

    patch(url, data = {}, conf = {}) {
        return this.client
            .patch(url, data, conf)
            .then((response) => Promise.resolve(response))
            .catch((error) => Promise.reject(error));
    }
}

export default new ApiClient(`${window.location.origin}/api`);
