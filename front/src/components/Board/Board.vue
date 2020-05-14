<template>
  <!-- <div class="container"> -->
  <div class="lists">
    <List v-for="list in lists" :key="list.id" :list="list" class="box list" />
    <div class="box list">
      <b-field>
        <input v-model="title" class="input" placeholder="Новый лист" />
      </b-field>
      <b-field>
        <b-button @click="createList">Создать</b-button>
      </b-field>
    </div>
  </div>
</template>

<script>
import List from "../List/List";
import { mapState } from "vuex";
export default {
  props: ["desk"],
  components: { List },
  data() {
    return {
      title: ""
    };
  },
  computed: {
    ...mapState(["lists"]),
    boardId() {
      return this.$route.params.id;
    }
  },
  methods: {
    createList() {
      this.$store.dispatch("createList", {
        id: this.desk.id,
        title: this.title
      });
      this.title = "";
    }
  },
  watch: {
    boardId() {
      this.$store.dispatch("fetchLists", {
        id: this.boardId
      });
    }
  }
};
</script>

<style>
.lists {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
  height: 100%;
  min-height: 450px;
}
.list {
  margin: 0 20px 24px 0;
  min-width: 300px;
  background: #ebecf0;
}
</style>
