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
import { mapActions } from "vuex";

export default {
    name: "Login",
    data() {
        return {
            email: "",
            password: "",
        };
    },
    methods: {
        ...mapActions("auth", ["login"]),
        async attemptLogin() {
            try {
                await this.login({ email: this.email, password: this.password });
            } catch (e) {
                // failed to login
                if (e.response.status === 400) {
                    this.$buefy.toast.open({
                        duration: 5000,
                        message: `<b>Invalid Credentials</b>`,
                        position: 'is-top',
                        type: 'is-danger'
                    })
                }
            }
        },
    }
};
</script>
