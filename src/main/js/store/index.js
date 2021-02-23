import auth from "@/store/modules/auth";
import party from "@/store/modules/party";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        auth,
        party,
    },
});
