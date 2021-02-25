<template>
    <div>
        <section class="pb-5">
            <h1 class="title">Shoppe</h1>
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
                                <b-numberinput
                                    controls-position="compact"
                                    controls-rounded
                                    v-model="quantity"
                                ></b-numberinput>
                            </b-field>
                        </div>
                    </div>
                    <footer class="card-footer">
                        <a class="card-footer-item" @click="buyBait">Buy</a>
                    </footer>
                </div>
            </div>

            <div>
                <div class="card">
                    <header class="card-header">
                        <p class="card-header-title has-text-black-ter">
                            Traps
                        </p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <h2 class="title">Trap</h2>
                            <p class="subtitle">
                                Current: {{ user.weapon.cat.name }}
                            </p>
                            <b-field>
                                <b-select v-model="selectedTrap">
                                    <option
                                        :value="trap.name"
                                        v-for="(trap, index) in allTraps"
                                        :key="index"
                                    >
                                        {{
                                            `${trap.name} [Power: ${
                                                trap.power
                                            }] [Bonus: ${trap.bonus}] [Luck: ${
                                                trap.luck
                                            }] [Cost: ${trap.power * 100}]`
                                        }}
                                    </option>
                                </b-select>
                            </b-field>
                        </div>
                    </div>
                    <footer class="card-footer">
                        <a class="card-footer-item" @click="buyTrap">Buy</a>
                    </footer>
                </div>
            </div>

            <div>
                <div class="card">
                    <header class="card-header">
                        <p class="card-header-title has-text-black-ter">Base</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <h2 class="title">Base</h2>
                            <p class="subtitle">
                                Current: {{ user.weapon.base.name }}
                            </p>
                            <b-field>
                                <b-select v-model="selectedBase">
                                    <option
                                        :value="base.name"
                                        v-for="(base, index) in allBases"
                                        :key="index"
                                    >
                                        {{
                                            `${base.name} [Power: ${
                                                base.power
                                            }] [Bonus: ${base.bonus}] [Luck: ${
                                                base.luck
                                            }] [Cost: ${base.power * 100}]`
                                        }}
                                    </option>
                                </b-select>
                            </b-field>
                        </div>
                    </div>
                    <footer class="card-footer">
                        <a class="card-footer-item" @click="buyBase">Buy</a>
                    </footer>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
import { mapActions, mapState } from "vuex";
import http from "@/http";

export default {
    name: "ShopsIndex",
    data() {
        return {
            quantity: 1,
            selectedTrap: null,
            selectedBase: null,
            allTraps: [],
            allBases: [],
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
        },
        async buyTrap() {
            try {
                await http.post(`/shop/trap/${this.selectedTrap}`);
                await this.checkAuthStatus();
                this.$buefy.toast.open({
                    duration: 5000,
                    message: `<b>Bought ${this.selectedTrap}.</b>`,
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
        },
        async buyBase() {
            try {
                await http.post(`/shop/base/${this.selectedBase}`);
                await this.checkAuthStatus();
                this.$buefy.toast.open({
                    duration: 5000,
                    message: `<b>Bought ${this.selectedBase}.</b>`,
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
        },
    },
    computed: {
        ...mapState("auth", ["user"]),
    },
    created() {
        http.get(`/shop/trap`).then(({ data }) => this.allTraps = data);
        http.get(`/shop/base`).then(({ data }) => this.allBases = data);
        this.selectedTrap = this.user.weapon.cat.name;
        this.selectedBase = this.user.weapon.base.name;
    },
};
</script>
