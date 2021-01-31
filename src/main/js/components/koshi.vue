<template>
  <div class="container p-3 m-3">
    <h3>?aa</h3>
    <b-button @click="counter++">sameaa tbaah</b-button>
    <div>
      <h3 @click="counter++">aaaa{{ counter }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      counter: 0,
    };
  },
};
</script>
