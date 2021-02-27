import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const prefix = (prefix, routes) => {
    return routes.map((route) => {
        route.path = `/${prefix}${route.path}`;
        route.name = `${prefix}.${route.name}`;

        return route;
    });
};

const routes = [
    {
        path: "/",
        name: "index",
        component: require("@/views/Index").default,
        meta: { title: "Hunts" },
    },
    {
        path: "/shops",
        component: require("@/views/shops/Index").default,
        meta: { title: "Shops" },
    },
    {
        path: "/party",
        component: require("@/views/party/Index").default,
        meta: { title: "Party" },
    },
    {
        path: "/mice",
        component: require("@/views/mice/Index").default,
        meta: { title: "Mice Wiki" },
    },
    {
        path: "/score",
        component: require("@/views/score/Index").default,
        meta: { title: "Scoreboard" },
    },
    {
        path: "*",
        component: require("@/views/layouts/PageNotFound").default,
        meta: { title: "404" },
    },
];

export const router = new VueRouter({
    mode: "history",
    routes,
    linkExactActiveClass: "is-active",
});

/**
 * @param {string} defaultTitle
 */
export const registerDefaultTitle = (defaultTitle) => {
    router.afterEach((to, from) => {
        Vue.nextTick(() => {
            if (typeof to.meta?.title === "undefined") {
                document.title = defaultTitle;
                return;
            }

            let title =
                typeof to.meta.title === "function"
                    ? to.meta.title(to)
                    : to.meta.title;

            document.title = `${defaultTitle} :: ${title}`;
        });
    });
};
