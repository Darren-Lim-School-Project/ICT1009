<template>
    <b-navbar wrapper-class="container">
        <template slot="brand">
            <b-navbar-item>🐁</b-navbar-item>
        </template>
        <template slot="start">
            <template>
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
                            <b-icon icon="star-four-points"></b-icon>
                            {{ user.rank.points }}
                        </span>
                    </div>
                </b-navbar-item>
            </template>
        </template>
        <template slot="end">
            <b-navbar-item>
                <b-button @click="logout">Logout</b-button>
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
        ...mapActions("auth", ["logout", "checkAuthStatus"]),
    },
};
</script>
