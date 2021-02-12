import webstomp from "webstomp-client";
import VueRouter from "vue-router";
import Vue from "vue";
import { router } from "./router";
import Buefy, { NotificationProgrammatic as Notify } from "buefy";
import App from "@/views/App";

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
    router,
}).$mount("#app");
