<template>
  <article class="panel is-primary">
    <p class="panel-heading">Highest Ranks</p>
    <div
        class="panel-block"
        v-for="(user, index) in topScores"
        :key="user.id"
    >
                                <span class="panel-icon">
                                  {{ index + 1 }}
                                </span>
      <div>
                                    <span class="is-size-5">{{
                                        user.name
                                      }}</span>
        <p class="is-size-7">
          {{ user.trap }}
        </p>
        <p class="is-size-7">
          {{ user.base }}
        </p>
        <div
            class="field is-grouped is-grouped-multiline pt-1"
        >
          <div class="control">
            <div class="tags has-addons">
                                                <span class="tag is-dark"
                                                >Rank</span
                                                >
              <span class="tag is-primary">{{
                  startCase(
                      user.currentRank
                          .rankTitle,
                  )
                }}</span>
            </div>
          </div>
          <div class="control">
            <div class="tags has-addons">
                                                <span class="tag is-dark"
                                                >Points</span
                                                >
              <span class="tag is-primary">{{
                  startCase(
                      user.currentRank
                          .points,
                  )
                }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </article>
</template>

<script>
import http from '@/http';
import { startCase } from "lodash";

export default {
  data() {
    return {
      topScores: [],
    };
  },
  methods: { startCase },
  async created() {
    let { data } = await http.get("/auth/scores");
    this.topScores = data;
  },
};
</script>
