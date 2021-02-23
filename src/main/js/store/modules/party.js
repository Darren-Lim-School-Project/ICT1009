import http from "@/http";

const state = {
    party: null,
};

const getters = {
    inParty(state) {
        return !!state.party;
    },
    party(state) {
        return state.party;
    },
};

const actions = {
    async join({ commit }, room) {
        try {
            /** @type {AxiosResponse<*>} */
            let response = await http.post(`/party/join/${room}`);
            commit("setParty", response.data);

            return response.data;
        } catch (e) {
            console.log(e);
            throw e;
        }
    },
    async leave({ commit }) {
        await http.post("/party/leave");
        commit("setParty", null);
    },
    async checkPartyStatus({ commit }) {
        try {
            /** @type {AxiosResponse<*>} */
            let response = await http.get("/party/room");
            let party = response.data;
            commit("setParty", party);
        } catch (e) {
            // user not logged in
            if (e.response.status === 404) {
                commit("setParty", null);
                return;
            }

            // something else went wrong, bubble it up
            throw e;
        }
    },
};

const mutations = {
    setParty(state, party) {
        state.party = party;
    },
};

export default {
    state,
    getters,
    actions,
    mutations,
    namespaced: true,
};
