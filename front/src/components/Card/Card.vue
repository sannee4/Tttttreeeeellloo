<template>
  <div class="modal-card card-modal">
    <header class="modal-card-head">
      <p class="modal-card-title">{{card.title}}</p>
    </header>
    <section class="modal-card-body">
      <div class="box" v-for="comment in comments" :key="comment.id">
        <article class="media">
          <div class="media-content">
            <div class="content">
              <p>
                <strong>{{comment.user.firstName}} {{comment.user.lastName}}</strong>
                <small>@{{comment.user.username}}</small>
                <br />
                <time>{{comment.created | time}}</time>
                <br />
                {{comment.content}}
              </p>
            </div>
          </div>
        </article>
      </div>
      <div class="comment__btn">
        <textarea class="textarea" type="text" placeholder="Текст" v-model="content"></textarea>
        <button class="button btn-create" @click="createComment">Создать комментарий</button>
        <button class="button is-warning">Архивировать</button>
        <button class="button is-danger">Удалить</button>
        <button class="button" @click="close">Закрыть</button>
      </div>
    </section>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  props: ["card"],
  data() {
    return {
      content: ""
    };
  },
  computed: {
    ...mapState(["comments", "cardModalActive"])
  },
  created() {
    this.$store.dispatch("fetchComments", { id: this.card.id });
  },
  methods: {
    createComment() {
      //
      this.$store.dispatch("createComment", {
        id: this.card.id,
        content: this.content
      });
    },
    close() {
      this.$parent.close();
      // this.$store.commit("setCardModalActive", false);
    }
  }
};
</script>

<style>
.modal-card {
  width: 80vw;
}
.animation-content {
  max-width: none !important;
}
.btn-create {
  width: 100%;
}
</style>