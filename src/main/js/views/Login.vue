<template>
    <div class="container">
        <section class="hero is-fullheight">
            <div class="hero-body">
                <div class="container">
                    <div
                        class="column is-6-desktop is-offset-3-desktop is-full-tablet"
                    >
                        <h3 class="title has-text-black has-text-centered">
                            Login
                        </h3>
                        <hr class="login-hr" />
                        <div class="box">
                            <form>
                                <b-field
                                    label="Email"
                                    label-position="on-border"
                                >
                                    <b-input
                                        v-model="email"
                                        name="email"
                                        size="is-medium"
                                        type="email"
                                    ></b-input>
                                </b-field>
                                <b-field
                                    label="Password"
                                    label-position="on-border"
                                >
                                    <b-input
                                        v-model="password"
                                        name="password"
                                        size="is-medium"
                                        type="password"
                                        password-reveal
                                    ></b-input>
                                </b-field>
                                <button
                                    @click.prevent="attemptLogin"
                                    class="button is-block is-primary is-large is-fullwidth"
                                >
                                    Login
                                    <i class="mdi mdi-login"></i>
                                    <b-loading
                                        v-model="state.loading"
                                    ></b-loading>
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="is-divider" data-content="OR"></div>
                    <div
                        class="column is-6-desktop is-offset-3-desktop is-full-tablet"
                    >
                        <h3 class="title has-text-black has-text-centered">
                            Register
                        </h3>
                        <hr class="login-hr" />
                        <div class="box">
                            <form>
                                <b-field
                                    label="Name"
                                    label-position="on-border"
                                >
                                    <b-input
                                        v-model="regName"
                                        name="name"
                                        size="is-medium"
                                        type="text"
                                    ></b-input>
                                </b-field>
                                <b-field
                                    label="Email"
                                    label-position="on-border"
                                >
                                    <b-input
                                        v-model="regEmail"
                                        name="email"
                                        size="is-medium"
                                        type="email"
                                    ></b-input>
                                </b-field>
                                <b-field
                                    label="Password"
                                    label-position="on-border"
                                >
                                    <b-input
                                        v-model="regPassword"
                                        name="password"
                                        size="is-medium"
                                        type="password"
                                        password-reveal
                                    ></b-input>
                                </b-field>
                                <button
                                    @click.prevent="attemptRegistration"
                                    class="button is-block is-primary is-large is-fullwidth"
                                >
                                    Register
                                    <i class="mdi mdi-login"></i>
                                    <b-loading
                                        v-model="state.loading"
                                    ></b-loading>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
import { mapActions, mapMutations } from "vuex";
import http from "@/http";

export default {
    name: "Login",
    data() {
        return {
            state: {
                loading: false,
            },
            email: "",
            password: "",
            regName: "",
            regEmail: "",
            regPassword: "",
        };
    },
    methods: {
        ...mapActions("auth", ["login"]),
        ...mapMutations("auth", ["setUser"]),
        async attemptLogin() {
            this.state.loading = true;
            try {
                await this.login({ email: this.email, password: this.password });
            } catch (e) {
                // failed to login
                if (e.response.status === 400) {
                    this.$buefy.toast.open({
                        duration: 5000,
                        message: `<b>Invalid Credentials</b>`,
                        position: "is-top",
                        type: "is-danger",
                    });
                }
            } finally {
                this.state.loading = false;
            }

        },
        async attemptRegistration() {
            this.state.loading = true;
            try {
                let { data } = await http.post("/auth/register", {
                    name: this.regName,
                    email: this.regEmail,
                    password: this.regPassword,
                });
                this.setUser(data);
            } catch (e) {
                if (e.response.status === 400) {
                    this.$buefy.toast.open({
                        duration: 5000,
                        message: `<b>User already exists</b>`,
                        position: "is-top",
                        type: "is-danger",
                    });
                }
            } finally {
                this.state.loading = false;
            }
        },
    },
};
</script>
