<template>
    <div>
        <section class="pb-5">
            <h1 class="title">Cheese Shop</h1>
            <div>
                <div class="card">
                    <header class="card-header">
                        <p class="card-header-title has-text-black-ter">
                            Buy Cheese
                        </p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <h2 class="title">Quantity</h2>
                            <p class="subtitle">Each bait costs 200</p>
                            <b-field>
                                <b-input
                                    type="number"
                                    min="1"
                                    v-model="quantity"
                                >
                                </b-input>
                            </b-field>
                        </div>
                    </div>
                    <footer class="card-footer">
                        <a class="card-footer-item" @click="buyBait">Buy</a>
                    </footer>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
import { mapActions } from "vuex";
import http from "@/http";

export default {
    name: "ShopsIndex",
    data() {
        return {
            quantity: 1
        };
    },
    methods: {
        ...mapActions("auth", ["checkAuthStatus"]),
        async buyBait() {
            try {
                await http.post(`/shop/${this.quantity}`);
                await this.checkAuthStatus();
                this.$buefy.toast.open({
                    duration: 5000,
                    message: `<b>Bought ${this.quantity} cheese.</b>`,
                    position: "is-bottom",
                    type: "is-success",
                });
            } catch (e) {
                if (e.response.status === 400) {
                    this.$buefy.toast.open({
                        duration: 5000,
                        message: `<b>Insufficient Credits</b>`,
                        position: "is-bottom",
                        type: "is-danger",
                    });
                }
            }
        }
    },
    computed: {},
};
</script>
