<template>
  <div>
    <p>You've sounded the horn a total of {{ times }} times</p>
    <b-loading :is-full-page="true" v-model="isLoading"></b-loading>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      times: 0,
      isLoading: true,
    };
  },
  async mounted() {
    this.isLoading = true;
    await this.fetchHorns();
    this.isLoading = false;
  },
  methods: {
    async fetchHorns() {
      let { data } = await axios.get("/api/horns");
      this.times = data.times;
    },
  },
};
</script>
