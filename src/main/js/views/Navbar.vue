<template>
    <b-navbar wrapper-class="container">
        <template slot="brand">
            <b-navbar-item>🐁</b-navbar-item>
        </template>
        <template slot="end"></template>
        <template slot="start">
            <template v-if="signedIn">
                <b-navbar-item>
                    <div>
                        <span>
                            {{ `${rankTitle} (${rankPercentage}%)` }}
                        </span>
                        <br />
                        <b-progress
                            style="width: 100px"
                            type="is-info"
                            :value="rankPercentage"
                            :title="`${rankPercentage}%`"
                        />
                    </div>
                </b-navbar-item>
                <b-navbar-item>
                    <div>
                        <span>
                            <b-icon icon="cash"></b-icon> {{ user.gold }}
                        </span>
                        <br />
                        <span>
                            <b-icon icon="star-four-points"></b-icon> {{ user.rank.points }}
                        </span>
                    </div>
                </b-navbar-item>
            </template>
            <b-navbar-item v-else>
                <b-button @click="logUserIn">Login</b-button>
            </b-navbar-item>
        </template>
    </b-navbar>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";

export default {
    name: "Navbar",
    data() {
        return {};
    },
    computed: {
        ...mapState("auth", ["user"]),
        ...mapGetters("auth", ["rankTitle", "rankPercentage", "signedIn"]),
    },
    methods: {
        ...mapActions("auth", ["login", "logout", "checkAuthStatus"]),
        logUserIn() {
            this.login({
                email: "x",
                password: "secret",
            });
        },
    },
    created() {
        this.checkAuthStatus()
            .catch(() => this.logout());
    },
};
</script>
