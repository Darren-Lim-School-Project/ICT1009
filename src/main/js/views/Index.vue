<template>
    <div class="container">
        <div>
            <div class="pb-5 px-3">
                <div>
                    <div>
                        <section class="is-flex pb-5">
                            <div class="is-justify-content-flex-start mr-auto">
                                <h1 class="title">Camp</h1>
                                <h2 class="subtitle">
                                    {{ user.bait }} bait left.
                                </h2>
                            </div>
                            <div class="is-justify-content-flex-end ml-auto">
                                <b-field>
                                    <p class="control">
                                        <b-dropdown v-model="selectedLocation">
                                            <template #trigger>
                                                <b-button
                                                    :label="
                                                        startCase(
                                                            selectedLocation.name
                                                        )
                                                    "
                                                    icon-right="menu-down"
                                                />
                                            </template>

                                            <b-dropdown-item
                                                v-for="(location, index) in locations"
                                                :key="index"
                                                :value="location"
                                            >
                                                {{ startCase(location.name) }}
                                            </b-dropdown-item>
                                        </b-dropdown>
                                    </p>
                                    <p class="control">
                                        <b-button
                                            class="button is-primary"
                                            @click="travelToLocation"
                                            >Travel
                                        </b-button>
                                    </p>
                                    <b-button
                                        class="mx-3"
                                        type="is-success"
                                        @click="startHunt"
                                        >{{ state.huntButton }}
                                        <b-loading
                                            :is-full-page="false"
                                            :active="state.hunting"
                                        ></b-loading>
                                    </b-button>
                                </b-field>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
        <div class="pt-4">
            <article class="panel is-primary">
                <div class="card-content">
                    <div class="media">
                        <div class="media-content">
                            <p class="title is-4">{{ user.name }}</p>
                        </div>
                    </div>
                    <div class="columns">
                        <div class="column pl-0">
                            <p class="is-5 has-text-weight-bold">Trap</p>
                            <p>{{ user.weapon.cat.name }}</p>
                            <div
                                class="field is-grouped is-grouped-multiline pt-1"
                            >
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Power</span>
                                        <span class="tag is-info">{{
                                            user.weapon.cat.power
                                        }}</span>
                                    </div>
                                </div>
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Bonus</span>
                                        <span class="tag is-info"
                                            >{{ user.weapon.cat.bonus }}%</span
                                        >
                                    </div>
                                </div>
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Luck</span>
                                        <span class="tag is-info">{{
                                            user.weapon.cat.luck
                                        }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="is-divider-vertical"></div>
                        <div class="column pl-0">
                            <p class="is-5 has-text-weight-bold">Base</p>
                            <p>{{ user.weapon.base.name }}</p>
                            <div
                                class="field is-grouped is-grouped-multiline pt-1"
                            >
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Power</span>
                                        <span class="tag is-info">{{
                                            user.weapon.base.power
                                        }}</span>
                                    </div>
                                </div>
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Bonus</span>
                                        <span class="tag is-info"
                                            >{{ user.weapon.base.bonus }}%</span
                                        >
                                    </div>
                                </div>
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Luck</span>
                                        <span class="tag is-info">{{
                                            user.weapon.base.luck
                                        }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="is-divider-vertical"></div>
                        <div class="column pl-0">
                            <p class="has-text-weight-bold">Weapon</p>
                            <p>Total</p>
                            <div
                                class="field is-grouped is-grouped-multiline pt-1"
                            >
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark"
                                            >Power</span
                                        >
                                        <span class="tag is-success">{{
                                            user.weapon.totalTrapPower
                                        }}</span>
                                    </div>
                                </div>
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-dark">Luck</span>
                                        <span class="tag is-success">{{
                                            user.weapon.totalTrapLuck
                                        }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </article>
        </div>
        <div class="pt-4">
            <article class="panel is-info">
                <p class="panel-heading">
                    Hunter's Journal - {{ startCase(user.location) }}
                </p>
                <a
                    :class="{
                        'is-flex': true,
                        'panel-block': true,
                        'has-background-danger-light':
                            hunt.catchState === 'FAILED',
                        'has-background-success-light':
                            hunt.catchState !== 'FAILED',
                    }"
                    v-for="(hunt, index) in huntLog"
                    :key="index"
                >
                    <span class="panel-icon ml-3 is-justify-content-flex-start">
                        <i
                            :class="{
                                mdi: true,
                                'mdi-check': hunt.catchState !== 'FAILED',
                                'mdi-close-thick': hunt.catchState === 'FAILED',
                            }"
                            aria-hidden="true"
                        ></i>
                    </span>
                    <p>{{ hunt.catchOutcome }}</p>
                    <span class="pr-3 is-justify-content-flex-end ml-auto">{{
                        fuzzyDate(hunt.createdAt)
                    }}</span>
                </a>
            </article>
        </div>
    </div>
</template>
<script>
import { socketClient } from "@/app";
import { startCase } from "lodash";
import { mapActions, mapGetters, mapState } from "vuex";
import http from "@/http";
import { fuzzyDate } from "@/formatters";

export default {
    name: "MainIndex",
    data() {
        return {
            party: {},
            huntLog: [],
            locations: [],
            selectedLocation: 0,
            state: {
                huntButton: "Start Hunt",
                hunting: false,
            },
        };
    },
    computed: {
        ...mapState("auth", ["user"]),
    },
    methods: {
        startCase,
        fuzzyDate,
        ...mapActions("auth", ["checkAuthStatus"]),
        async fetchLocations() {
            let { data } = await http.get("/location");
            this.locations = data;
            this.selectedLocation = { name: this.user.location };
        },
        async fetchHunts() {
            let { data } = await http.get("/hunt");
            this.huntLog = data;
        },
        async travelToLocation() {
            let selected = this.selectedLocation.name;
            await http.post(`/location/${selected}`);
            this.huntLog.unshift({ catchOutcome: `I travelled to the ${startCase(selected)}.` });
        },
        async startHunt() {
            let { data } = await http.post(`/hunt/newHunt`);
            if (data.tooEarly !== undefined) {
                this.$buefy.toast.open({
                    duration: 5000,
                    message: `<b>Too early! Wait for ${data.tooEarly} more seconds.</b>`,
                    position: "is-bottom",
                    type: "is-danger",
                });
            }

            if (data.noBait === true) {
                this.$buefy.toast.open({
                    duration: 5000,
                    message: `<b>Out of bait!</b>`,
                    position: "is-bottom",
                    type: "is-danger",
                });
            }
        },
        async subscribe() {
            await socketClient.listen(`hunt/${this.user.id}`, response => {
                let hunt = JSON.parse(response.body);
                this.huntLog.unshift(hunt);
                this.checkAuthStatus();
            });
        },
    },
    async created() {
        await this.fetchLocations();
        await this.fetchHunts();
        await this.subscribe();
    },
};
</script>
