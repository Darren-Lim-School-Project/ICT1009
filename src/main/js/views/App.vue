<template>
    <div>
        <template v-if="signedIn">
            <Navbar />
            <div class="tabs is-centered">
                <ul>
                    <router-link tag="li" to="/"><a>Camp</a></router-link>
                    <router-link tag="li" to="/travel"
                        ><a>Travel</a></router-link
                    >
                    <router-link tag="li" to="/inventory"
                        ><a>Inventory</a></router-link
                    >
                    <router-link tag="li" to="/shops"><a>Shops</a></router-link>
                    <router-link tag="li" to="/mice"><a>Mice</a></router-link>
                </ul>
            </div>
            <div class="container px-3">
                <router-view></router-view>
            </div>
        </template>
        <template v-else>
            <Login />
        </template>
    </div>
</template>
<script>
import Navbar from "@/views/Navbar";
import { mapActions, mapGetters } from "vuex";
import Login from "@/views/Login";

export default {
    name: "App",
    components: { Navbar, Login },
    computed: {
        ...mapGetters("auth", ["signedIn"]),
    },
    methods: {
        ...mapActions("auth", ["checkAuthStatus"])
    },
    created() {
        this.checkAuthStatus()
            .catch(() => this.logout());
    },
};
</script>
