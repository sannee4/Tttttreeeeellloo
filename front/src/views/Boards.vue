<template>
  <section class="section">
    <div class="container">
      <CreateBoard />
      <div class="cards">
        <div class="card board-card" v-for="board in desks" :key="board.id">
          <div class="card-content">
            <div class="media">
              <div class="media-content">
                <p class="title is-4">{{ board.name }}</p>
              </div>
            </div>

            <div class="content">
              Пользователи:
              <p
                class="has-text-left"
                v-for="(user, idx) in board.users"
                :key="idx"
              >
                {{ user.firstName }} {{ user.lastName }}
              </p>
              <time datetime="2016-1-1">{{ board.created | time }}</time>
              <div class="">
                <router-link :to="'/' + board.id">Перейти</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import CreateBoard from "../components/Board/CreateBoard";
import { mapState } from "vuex";

export default {
  components: { CreateBoard },
  data() {
    return {
      boards: [],
    };
  },
  computed: {
    ...mapState(["desks"]),
  },
  beforeMount() {
    this.$store.dispatch("fetchDesks");
  },
};
</script>

<style scoped>
.cards {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.board-card {
  margin: 20px;
}
</style>
