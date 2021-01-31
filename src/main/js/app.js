import webstomp from "webstomp-client";
import VueRouter from 'vue-router';
import Vue from 'vue';
import Buefy from 'buefy';

Vue.use(VueRouter);
Vue.use(Buefy);
const stompClient = webstomp.client("ws://localhost:8080/chat");
stompClient.connect({}, () => {
    console.log("connected");
    stompClient.subscribe("/topic/messages", x => {
        console.log(x);
        x.ack();
    });
});

window.sendit = () => {
    stompClient.send("/app/chat", JSON.stringify({ 'from': "me", 'text': "lmao" }));
};

const routes = [
    { path: '/', redirect: '/hunt' },
    { path: '/hunt', component: require("./components/Hunt").default },
    { path: '/status', component: require('./components/Status').default },
];

const router = new VueRouter({
    mode: "history",
    routes,
    linkActiveClass: 'is-active',
});

new Vue({
    router,
}).$mount("#app");
