<template>
    <div>
        <label for="min"
            >Minutes<br />
            <input
                type="number"
                v-model="minutes"
                name="time_m"
                id="min"
                min="0"
                max="59"
            />
        </label>
        <label for="sec"
            >Secondes<br />
            <input
                type="number"
                v-model="secondes"
                name="time_s"
                id="sec"
                max="59"
                min="0"
            />
        </label>
        <button type="button" @click="sendTime">Set time</button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            minutes: 0,
            secondes: 0,
        };
    },
    methods: {
        sendTime() {
            this.$emit("set-time", { minutes: this.minutes, secondes: this.secondes });
        },
    },
};
</script>
