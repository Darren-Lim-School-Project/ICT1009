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
        try {
            /** @type {AxiosResponse<*>} */
            let response = await http.post("/auth/login", credentials);
            commit("setUser", response.data);
        } catch (e) {
            console.log(e);

            throw e;
        }
    },
    async logout({ commit }) {
        try {
            await http.post("/auth/logout");
        } catch (e) {
            console.log(`already logged out: ${e}`);
        }

        commit("setUser", null);
    },
    async checkAuthStatus({ commit }) {
        try {
            /** @type {AxiosResponse<*>} */
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
