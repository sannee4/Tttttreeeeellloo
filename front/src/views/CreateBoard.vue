<template>
  <section class="section">
    <div class="container">
      <div class="columns is-centered"></div>
      <div class="column is-4">
        <form class="box">
          <b-field label="Название">
            <b-input v-model="name" placeholder="Название доски"></b-input>
          </b-field>
          <b-field>
            <b-button @click="create" type="is-light">Создать</b-button>
          </b-field>
        </form>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      name: ""
    };
  },
  methods: {
    create() {
      this.$store.dispatch("createDesk", { name: this.name });
      this.$router.push("/");
    }
  }
};
</script>

<style>
</style>