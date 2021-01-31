<template>
  <div>
    <b-button type="is-primary" @click="sendIt">Sound it</b-button>
    <ul v-if="buffer.length > 0">
      <li v-for="(line, index) in buffer" :key="index">
        {{ line }}
      </li>
    </ul>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      response: "",
      buffer: [],
    };
  },
  methods: {
    async sendIt() {
      let { data } = await axios.post("/api/sound");

      this.buffer.unshift(data.reply);
    },
  },
};
</script>
