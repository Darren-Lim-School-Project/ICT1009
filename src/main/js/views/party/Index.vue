<template>
    <div>
        <div class="pb-5 px-3">
            <div v-if="inParty">
                <h1 class="title">
                    {{ party.room }}
                    <b-button type="is-danger" @click="leaveParty"
                        >Leave
                    </b-button>
                </h1>
                <div>
                    <article class="panel is-primary">
                        <p class="panel-heading">Members</p>
                        <a
                            class="panel-block"
                            :class="{
                                'is-active': partyMember.id === user.id,
                            }"
                            v-for="partyMember in party.users"
                            :key="partyMember.id"
                        >
                            <span class="panel-icon">
                                <i
                                    class="mdi mdi-account"
                                    aria-hidden="true"
                                ></i>
                            </span>
                            {{ partyMember.name }}
                        </a>
                    </article>
                </div>
            </div>
            <div v-else>
                <h1 class="title">Join a Party</h1>
                <b-field label="Room" label-position="on-border">
                    <b-input
                        v-model="roomToJoin"
                        name="room"
                        size="is-medium"
                        type="text"
                        @keyup.native.enter="joinParty"
                    ></b-input>
                </b-field>
                <b-button type="is-primary" @click="joinParty">Join</b-button>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import http from "@/http";
import { socketClient } from "@/app";

export default {
    name: "PartyIndex",
    data() {
        return {
            roomToJoin: "",
            party: {},
        };
    },
    methods: {
        ...mapActions("party", ["join", "leave"]),
        async joinParty() {
            await this.join(this.roomToJoin);
            await this.updateParty();
            await this.subscribe();
        },
        async leaveParty() {
            await this.leave();
            await this.updateParty();
        },
        async updateParty() {
            try {
                let { data } = await http.get("/party/room");
                this.party = data;
            } catch (e) {
                if (e.response.status === 404) {
                    this.party = { users: [] };
                }
            }
        },
        async subscribe() {
            if (!this.party) {
                return;
            }

            await socketClient.listen(`join/room/${this.party.room}`, response => {
                console.log(response);
                this.party.users = JSON.parse(response.body);
            });
            await socketClient.listen(`leave/room/${this.party.room}`, response => {
                console.log(response);
                this.party.users = JSON.parse(response.body);
            });
        }
    },
    computed: {
        ...mapGetters("party", ["inParty"]),
        ...mapState("auth", ["user"]),
    },
    async created() {
        await this.updateParty();
        await this.subscribe();
    },
};
</script>
