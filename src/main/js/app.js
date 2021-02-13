import store from "@/store";
import App from "@/views/App";
import Buefy, { NotificationProgrammatic as Notify } from "buefy";
import Vue from "vue";
import VueRouter from "vue-router";
import webstomp from "webstomp-client";

import { router } from "./router";

Vue.use(VueRouter);
Vue.use(Buefy);

Vue.config.errorHandler = (err, vm, info) => {
    Notify.open({
        duration: 15000,
        message: err.toString(),
        hasIcon: true,
        queue: false,
        position: "is-bottom-right",
        type: "is-danger",
    });

    console.log(err);
};

new Vue({
    render: (h) => h(App),
    store,
    router,
}).$mount("#app");
