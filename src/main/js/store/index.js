import auth from "@/store/modules/auth";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        auth,
    },
});
