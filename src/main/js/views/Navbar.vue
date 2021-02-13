<template>
    <b-navbar wrapper-class="container">
        <template slot="brand">
            <b-navbar-item tag="router-link" :to="{ name: 'index' }"
                >🐁
            </b-navbar-item>
        </template>
        <template slot="start">
            <b-navbar-item> Rodent Raid</b-navbar-item>
        </template>
        <template slot="end">
            <b-navbar-item>
                <b-button @click="logUserIn">Login</b-button>
            </b-navbar-item>
            <b-navbar-item>
                <br />
            </b-navbar-item>
        </template>
    </b-navbar>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
    name: "Navbar",
    data() {
        return {};
    },
    computed: {
        ...mapState("auth", ["user"]),
    },
    methods: {
        ...mapActions("auth", ["login", "logout"]),
        logUserIn() {
            this.login({
                email: "x",
                password: "secret",
            });
        },
    },
    created() {},
};
</script>
