<template>
  <div class="box list" :id="list.id" @dragover.prevent @drop.prevent="drop">
    <h1 class="list-title">{{ list.title }}</h1>
    <div class="cards">
      <div
        v-show="list.cards"
        class="box list-card"
        v-for="card in list.cards"
        :key="card.id"
        :id="card.id"
        :draggable="!cardModalActive"
        :style="{ 'cursor': cardModalActive ? 'default': 'pointer'}"
        @dragstart="dragStart"
        @dragover.stop
        @click="openModal"
      >
        {{card.title}}
        <b-modal
          :active.sync="cardModalActive"
          has-modal-card
          trap-focus
          :destroy-on-hide="false"
          aria-role="dialog"
          aria-modal
        >
          <Card :card="card" />
        </b-modal>
      </div>
      <div v-if="createActive" class="box list-card">
        <b-field>
          <input v-model="title" class="input" placeholder="Название карточки" />
        </b-field>
        <b-field label="Select datetime">
          <b-datetimepicker
            rounded
            v-model="deadline"
            placeholder="Click to select..."
            icon="calendar-today"
            horizontal-time-picker
          ></b-datetimepicker>
        </b-field>
        <b-field>
          <button class="button is-medium" @click="createCard">Создать</button>
        </b-field>
      </div>
      <button class="button is-medium" @click="() => (createActive = true)" v-else>Добавить карточку</button>
    </div>
  </div>
</template>

<script>
import Card from "../Card/Card";
// import { mapState } from "vuex";
export default {
  props: ["list"],
  components: { Card },
  data() {
    return {
      createActive: false,
      title: "",
      deadline: null,
      cardModalActive: false
    };
  },
  methods: {
    createCard() {
      this.$store.dispatch("createCard", {
        listId: this.list.id,
        title: this.title,
        deadline: this.deadline
      });
      this.title = "";
      this.deadline = null;
      this.createActive = false;
    },
    openModal() {
      this.cardModalActive = true;
      // this.$store.commit("setCardModalActive", true);
    },
    dragStart: e => {
      const target = e.target;
      e.dataTransfer.setData("card_id", target.id);
      setTimeout(() => {
        target.style.display = "none";
      }, 0);
    },
    drop(e) {
      const card_id = e.dataTransfer.getData("card_id");
      const card = document.getElementById(card_id);
      card.style.display = "block";

      e.target.appendChild(card);
      this.$store.dispatch("moveCard", {
        cardId: +card_id,
        listId: this.list.id
      });
    }
  }
};
</script>

<style>
.list {
  display: flex;
  flex-direction: column;
}
.list-title {
  padding-bottom: 10px;
  font-size: 22px;
  line-height: 20px;
  font-weight: 600;
}
.list-card {
  margin-bottom: 15px !important;
  padding: 10px;
  text-align: left;
  cursor: pointer;
}
</style>
