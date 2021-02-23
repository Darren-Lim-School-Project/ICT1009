<template>
    <div>
        <div class="pb-5 px-3">
            <div v-if="inParty">
                <div>
                    <section class="is-flex pb-5">
                        <div class="is-justify-content-flex-start mr-auto">
                            <h1 class="title">
                                {{ party.room }}
                            </h1>
                        </div>
                        <div class="is-justify-content-flex-end ml-auto">
                            <b-button type="is-success" @click="startHunt"
                                >{{ state.huntButton }}
                                <b-loading
                                    :is-full-page="false"
                                    :active="state.hunting"
                                ></b-loading>
                            </b-button>
                            <b-button type="is-danger" @click="leaveParty"
                                >Leave
                            </b-button>
                        </div>
                    </section>
                    <div>
                        <article class="panel is-primary">
                            <p class="panel-heading">Members</p>
                            <div
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
                                <div>
                                    <span class="is-size-5">{{
                                        partyMember.name
                                    }}</span>
                                    <p class="is-size-7">
                                        {{ partyMember.trap }}
                                    </p>
                                    <p class="is-size-7">
                                        {{ partyMember.base }}
                                    </p>
                                    <div
                                        class="field is-grouped is-grouped-multiline pt-1"
                                    >
                                        <div class="control">
                                            <div class="tags has-addons">
                                                <span class="tag is-dark"
                                                    >Location</span
                                                >
                                                <span class="tag is-info">{{
                                                    partyMember.location
                                                }}</span>
                                            </div>
                                        </div>
                                        <div class="control">
                                            <div class="tags has-addons">
                                                <span class="tag is-dark"
                                                    >Rank</span
                                                >
                                                <span class="tag is-primary">{{
                                                    startCase(
                                                        partyMember.currentRank
                                                            .rankTitle
                                                    )
                                                }}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </article>
                    </div>
                </div>
                <div class="pt-4">
                    <div class="hero-body">
                        <div
                            style="
                                height: 30vh;
                                width: 100%;
                                overflow-y: auto;
                                overflow-x: hidden;
                            "
                            ref="msgdiv"
                        >
                            <p
                                v-for="(message, index) in chatLog"
                                :key="index"
                                :style="{
                                    padding: '.25em',
                                    textAlign: message.me ? 'left' : 'right',
                                    overflowWrap: 'normal',
                                }"
                            >
                                <b>{{ message.username }}</b>
                                <br>
                                <span
                                    :class="{
                                        tag: true,
                                        'is-medium': true,
                                        'is-success': message.me,
                                        'is-info': !message.me,
                                    }"
                                    >
                                    {{ message.message }}
                                </span
                                >
                            </p>
                        </div>
                    </div>

                    <div class="section is-small">
                        <div class="field has-addons">
                            <div class="control is-expanded">
                                <b-field
                                    label="Talk to party members"
                                    label-position="on-border"
                                >
                                    <b-input
                                        v-model="userInput"
                                        @keyup.enter.native="sendChatMessage"
                                        icon="account-voice"
                                    >
                                    </b-input>
                                </b-field>
                            </div>
                            <div class="control">
                                <b-button
                                    tabindex="-1"
                                    @click="sendChatMessage"
                                >
                                    Send
                                </b-button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pt-4">
                    <article class="panel is-info">
                        <p class="panel-heading">Party's Journal</p>
                        <a class="panel-block is-active">
                            <span class="panel-icon">
                                <i
                                    class="mdi mdi-pencil"
                                    aria-hidden="true"
                                ></i>
                            </span>
                            bulma
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
                    >
                        >
                    </b-input>
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
import { isEmpty, startCase } from "lodash";

export default {
    name: "PartyIndex",
    data() {
        return {
            roomToJoin: "",
            party: {},
            chatLog: [],
            userInput: "",
            state: {
                huntButton: "Start Hunt",
                hunting: false,
            },
        };
    },
    methods: {
        startCase,
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
            if (isEmpty(this.party.room)) {
                return;
            }

            await socketClient.listen(`join/room/${this.party.room}`, response => {
                this.party.users = JSON.parse(response.body);
            });

            await socketClient.listen(`leave/room/${this.party.room}`, response => {
                this.party.users = JSON.parse(response.body);
            });

            await socketClient.listen(`chat/${this.party.room}`, response => {
                let { username, message } = JSON.parse(response.body);
                console.log(response.body);
                this.chatLog.push({ username, message, me: username === this.user.name });
                this.$nextTick(() => {
                    let msgdiv = this.$refs.msgdiv;
                    msgdiv.scrollTop = msgdiv.scrollHeight;
                });
            });
        },
        async startHunt() {
            this.state.hunting = true;
            try {
                let { data } = await http.post("/party/startHunt");
            } catch (e) {
                if (e.response.status === 400) {
                    this.$buefy.toast.open({
                        duration: 5000,
                        message: `<b>${e.response.data.message}</b>`,
                        position: "is-bottom",
                        type: "is-danger",
                    });
                }
            } finally {
                this.state.hunting = false;
            }
        },
        async sendChatMessage() {
            if (this.userInput === "") {
                return;
            }
            await socketClient.broadcast(`chat/${this.party.room}`, {
                username: this.user.name,
                message: this.userInput,
            });
            this.userInput = "";

        },
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
