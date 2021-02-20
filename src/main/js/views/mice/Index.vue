<template>
    <div>
        <div class="pb-5 px-3">
            <h1 class="title">All Mice</h1>
            <b-field>
                <p class="control">
                    <b-dropdown v-model="location">
                        <template #trigger>
                            <b-button
                                :label="location === '' ? 'Locations' : startCase(location)"
                                icon-right="menu-down"
                            />
                        </template>

                        <b-dropdown-item value="">None</b-dropdown-item>
                        <b-dropdown-item
                            v-for="(location, index) in locations"
                            :key="index"
                            :value="location"
                            >
                            {{ startCase(location) }}
                        </b-dropdown-item>
                    </b-dropdown>
                </p>
                <b-input
                    icon="magnify"
                    type="search"
                    placeholder="Search..."
                    v-model="search"
                ></b-input>
            </b-field>
        </div>
        <b-loading :active="mice.length <= 0"></b-loading>
        <div v-if="mice.length > 0">
            <div class="columns" v-for="(slice, index) in chunk(filteredMice, 4)" :key="index">
                <div class="column" v-for="(row, j) in slice" :key="j">
                    <div class="card">
                        <div class="card-content">
                            <div class="content">
                                <h4 class="subtitle">{{ row.name }}</h4>
                                <b-taglist>
                                    <b-tag
                                        type="is-primary"
                                        size="is-medium"
                                        v-for="(location, index) of row.locations"
                                        :key="index"
                                        >{{ startCase(location.name) }}
                                    </b-tag>
                                </b-taglist>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { chunk, startCase } from "lodash";
import http from "@/http";

export default {
    name: "MiceIndex",
    data() {
        return {
            location: "",
            search: "",
            mice: [],
        };
    },
    methods: {
        chunk,
        startCase,
    },
    computed: {
        locations() {
            if (this.mice.length <= 0) {
                return [];
            }

            let availableLocations = new Set();
            this.mice.forEach(({ locations }) => {
                locations.forEach(({ name }) => {
                    availableLocations.add(name);
                });
            });

            return availableLocations;
        },
        filteredMice() {
            return this.mice.filter((mouse) => {
                if (this.location !== "") {
                    return mouse.locations.some(({ name }) => name === this.location);
                }

                return mouse.name.toLowerCase()
                            .indexOf(this.search.toLowerCase()) >= 0;
            });
        },
    },
    async created() {
        let response = await http.get("/mice");
        this.mice = response.data;
    },
};
</script>
