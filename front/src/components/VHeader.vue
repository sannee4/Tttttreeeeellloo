<template>
  <section class="section">
    <nav class="navbar" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <router-link to="/" class="navbar-item">My Trello</router-link>
        <a
          role="button"
          class="navbar-burger burger"
          aria-label="menu"
          aria-expanded="false"
          data-target="navbarBasicExample"
        >
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>
      <div class="navbar-start">
        <router-link to="/create-board" class="navbar-item">Создать доску</router-link>
      </div>

      <div class="navbar-end">
        <div class="navbar-item">
          <div v-if="me" class="me">
            <h1 class="me__username">{{ me.username }}</h1>
            <b-button @click="logout">Logout</b-button>
          </div>
          <div class="buttons" v-else>
            <router-link to="/register" class="button is-primary">Sign up</router-link>
            <router-link to="/login" class="button is-light">Sign in</router-link>
          </div>
        </div>
      </div>
    </nav>
  </section>
</template>

<script>
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["me", "currentDesk"])
  },
  methods: {
    logout() {
      this.$store.dispatch("logout");
      this.$router.go();
    }
  }
};
</script>

<style>
.me {
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
}
.me__username {
  margin-right: 10px;
}
</style>
