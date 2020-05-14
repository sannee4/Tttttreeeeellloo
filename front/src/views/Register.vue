<template>
  <section class="section">
    <div class="container">
      <div class="columns is-centered">
        <div class="box column is-5">
          <b-field label="First Name">
            <b-input v-model="firstName" password-reveal></b-input>
          </b-field>
          <b-field label="Last Name">
            <b-input v-model="lastName" password-reveal></b-input>
          </b-field>
          <b-field label="Email">
            <b-input type="email" v-model="email"></b-input>
          </b-field>

          <b-field label="Username" type="is-success" message="This username is available">
            <b-input v-model="username" type="text" maxlength="30"></b-input>
          </b-field>

          <b-field label="Password">
            <b-input type="password" v-model="password" password-reveal></b-input>
          </b-field>
          <b-field>
            <b-button @click="register" type="is-primary">Sign up!</b-button>
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
      email: "",
      username: "",
      password: "",
      firstName: "",
      lastName: ""
    };
  },
  methods: {
    async register() {
      try {
        const { user, token } = await auth.register(this.$data);
        localStorage.setItem("x-token", token);
        this.$store.commit("setMe", user);
      } catch (error) {
        console.log(error);
        // this.$router.go();
      }
    }
  }
};
</script>

<style>
.login-offer {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 100%;
  align-items: center;
}
.login-offer__item {
  margin-right: 20px;
}
</style>