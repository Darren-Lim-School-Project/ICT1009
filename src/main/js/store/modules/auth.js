import { capitalize, roundTo } from "@/formatters";
import http from "@/http";

const state = {
    user: null,
};

const getters = {
    signedIn(state) {
        return !!state.user;
    },
    rankTitle(state) {
        return capitalize(state.user?.rank?.rankTitle);
    },
    rankPercentage(state) {
        return roundTo(state.user?.rank?.percentage * 100);
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
    async logout({ commit }) {
        await http.post("/auth/logout");
        commit("setUser", null);
    },
    async checkAuthStatus({ commit }) {
        /** @type {AxiosResponse<*>} */
        try {
            let response = await http.get("/auth/me");
            commit("setUser", response.data);
        } catch (e) {
            // user not logged in
            if (e.response.status === 401) {
                commit("setUser", null);
                return;
            }

            // something else went wrong, bubble it up
            throw e;
        }
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
