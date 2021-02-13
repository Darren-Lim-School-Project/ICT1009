import http from "@/http";

const state = {
    user: null,
};

const getters = {
    signedIn(state) {
        return !!state.user;
    },
};

const actions = {
    async login({ commit }, credentials) {
        let response;
        try {
            /** @type {AxiosResponse<*>} */
            response = await http.post("/auth/login", credentials);
        } catch (e) {
            console.log(e);

            throw e;
        }

        commit("setUser", response.data);
    },
    logout({ commit, dispatch }) {
        commit("setUser", null);
    },
    async checkAuthStatus({ commit }) {
        /** @type {AxiosResponse<*>} */
        let response = await http.get("/auth/me");
        commit("setUser", response.data);
    },
};

const mutations = {
    setUser(state, user) {
        state.user = user;
    },
};

export default {
    state,
    getters,
    actions,
    mutations,
    namespaced: true,
};
