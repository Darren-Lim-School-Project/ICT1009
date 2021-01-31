import webstomp from "webstomp-client";
import VueRouter from "vue-router";
import Vue from "vue";
import Buefy from "buefy";

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
    router,
}).$mount("#app");
