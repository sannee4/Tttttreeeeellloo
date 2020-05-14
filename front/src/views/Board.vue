<template>
  <section class="section" v-show="listsLoaded">
    <h1>{{ currentDesk.name }}</h1>
    <b-tabs position="is-centered" class="block">
      <b-tab-item label="Доска">
        <FullBoard :desk="currentDesk" />
      </b-tab-item>
      <b-tab-item label="Пользователи">
        <div class="container">
          <!-- <div class="add-user">
            <input
              @keyup="addUser"
              v-model="email"
              placeholder="Эл. адрес пользователя"
            />
          </div>-->
          <table class="table">
            <thead>
              <tr>
                <th>
                  <abbr title="User id">id</abbr>
                </th>
                <th>Эл. Адрес</th>
                <th>
                  <abbr title="name">Имя</abbr>
                </th>
                <th>
                  <abbr title="Surname">Фамилия</abbr>
                </th>
                <th>Никнейм</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in currentDesk.users" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.email }}</td>
                <td>{{ user.firstName }}</td>
                <td>{{ user.lastName }}</td>
                <td>{{ user.username }}</td>
              </tr>
            </tbody>
          </table>
          <div class="add-user">
            <b-field label="Хотите добавить пользователя?">
              <b-input placeholder="Эл. Адрес пользователя" v-model="email"></b-input>
            </b-field>
            <b-field>
              <b-button @click="addUser">Добавить</b-button>
            </b-field>
          </div>
        </div>
      </b-tab-item>
    </b-tabs>
  </section>
</template>

<script>
import FullBoard from "../components/Board/Board";
import { mapState } from "vuex";

export default {
  components: { FullBoard },
  computed: {
    ...mapState(["currentDesk", "listsLoaded"])
  },
  methods: {
    addUser() {
      const email = this.email;
      this.$store.dispatch("addUser", {
        id: +this.$route.params.id,
        email
      });
      this.email = "";
    }
  },
  async mounted() {
    const { id } = this.$route.params;
    try {
      this.$store.dispatch("currentDesk", { id });
    } catch (error) {
      this.$router.push("/");
    }
  }
};
</script>

<style>
.add-user {
  width: 100%;
  background: #d3d3d3;
  padding: 20px;
  cursor: pointer;
}
</style>
