import { registerDefaultTitle, router } from "@/router";
import { SocketClient } from "@/stomp";
import store from "@/store";
import App from "@/views/App";
import Buefy, { NotificationProgrammatic as Notify } from "buefy";
import Vue from "vue";
import VueRouter from "vue-router";

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

const wsHost = process.env.MIX_PRODUCTION
    ? `wss://${window.location.hostname}`
    : `ws://${window.location.hostname}:8080`;
export const socketClient = new SocketClient(
    wsHost,
    !process.env.MIX_PRODUCTION
);

registerDefaultTitle("Rodent Raid");

new Vue({
    render: (h) => h(App),
    store,
    router,
}).$mount("#app");
