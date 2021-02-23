<template>
    <div>
        <b-loading v-model="state.loading"></b-loading>
        <template v-if="signedIn">
            <Navbar />
            <div class="tabs is-centered">
                <ul>
                    <router-link tag="li" to="/"><a>Camp</a></router-link>
                    <router-link tag="li" to="/travel"
                        ><a>Travel</a></router-link
                    >
                    <router-link tag="li" to="/party"><a>Party</a></router-link>
                    <router-link tag="li" to="/shops"><a>Shops</a></router-link>
                    <router-link tag="li" to="/mice"><a>Mice</a></router-link>
                </ul>
            </div>
            <div class="container px-3">
                <router-view></router-view>
            </div>
        </template>
        <Login v-else />
    </div>
</template>
<script>
import Navbar from "@/views/Navbar";
import { mapActions, mapGetters } from "vuex";
import Login from "@/views/Login";

export default {
    name: "App",
    components: { Navbar, Login },
    data() {
        return {
            state: { loading: true },
        };
    },
    computed: {
        ...mapGetters("auth", ["signedIn"]),
    },
    methods: {
        ...mapActions("auth", ["checkAuthStatus", "logout"]),
        ...mapActions("party", ["checkPartyStatus"]),
    },
    async created() {
        try {
            await this.checkAuthStatus();
            await this.checkPartyStatus();
        } catch (e) {
            if (e.response.status !== 401) {
                await this.logout();
            }
        } finally {
            this.state.loading = false;
        }
    },
};
</script>
