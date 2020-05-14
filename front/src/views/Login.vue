<template>
  <section class="section">
    <div class="container">
      <div class="columns is-centered">
        <div class="box column is-5">
          <b-field label="Username" type="is-success" message="This username is available">
            <b-input v-model="username" type="text" maxlength="30"></b-input>
          </b-field>

          <b-field label="Password">
            <b-input type="password" v-model="password" password-reveal></b-input>
          </b-field>
          <b-field>
            <b-button @click="login" type="is-light">Sign in</b-button>
          </b-field>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import * as auth from "../service/auth";

export default {
  data() {
    return {
      username: "",
      password: ""
    };
  },
  methods: {
    async login() {
      try {
        const { user, token } = await auth.login(this.$data);
        localStorage.setItem("x-token", token);
        this.$store.commit("setMe", user);
      } catch (error) {
        console.log(error);
        this.$router.go();
      }
    }
  }
};
</script>

<style>
</style>