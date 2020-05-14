import Vue from "vue";
import Vuex from "vuex";
import * as auth from "../service/auth";
import * as desks from "../service/desks";
import * as lists from "../service/lists";
import * as cards from "../service/cards";
import * as comments from "../service/comments";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    me: null,
    lists: [],
    currentDesk: null,
    desks: [],
    comments: [],
    checks: [],
    listsLoaded: false,
    cardModalActive: false,
  },
  mutations: {
    setMe(state, payload) {
      state.me = payload;
    },
    addDesk(state, payload) {
      state.desks = [payload, ...state.desks];
    },
    addDesks(state, payload) {
      state.desks = payload;
    },
    addUserToDesk(state, payload) {
      state.desks = state.desks.map((x) => (x.id === payload.id ? payload : x));
    },
    currentDesk(state, payload) {
      state.listsLoaded = false;
      state.currentDesk = payload;
    },
    lists(state, payload) {
      state.lists = payload;
      state.listsLoaded = true;
    },
    newList(state, payload) {
      state.lists = [...state.lists, payload];
    },
    addCard(state, { listId, ...card }) {
      const data = state.lists.find((x) => x.id === listId);
      if (!data.cards) {
        data.cards = [];
      }
      data.cards.unshift(card);
    },
    setCardModalActive(state, payload) {
      state.cardModalActive = payload;
    },
    addComments(state, payload) {
      state.comments = payload;
    },
    addComment(state, payload) {
      state.comments = [payload, ...state.comments];
    },
  },
  actions: {
    async onVisit(store) {
      const token = localStorage.getItem("x-token");

      if (!token || store.state.me) {
        return;
      }

      try {
        const { user } = await auth.me();
        store.commit("setMe", user);
      } catch (error) {
        localStorage.removeItem("x-token");
        console.log("Auth error");
      }
    },
    async login(store, data) {
      try {
        const { token, user } = await auth.login(data);
        localStorage.setItem("x-token", token);
        store.commit("setMe", user);
      } catch (error) {
        store.commit("setMe", null);
      }
    },
    async createDesk(store, { name }) {
      const { desk } = await desks.create(name);
      store.commit("addDesk", desk);
    },
    async fetchDesks(store) {
      const { content } = await desks.list();
      store.commit("addDesks", content);
    },
    async addUser(store, { id, email }) {
      const data = await desks.addUser(id, email);
      store.commit("currentDesk", data);
      store.commit("addUserToDesk");
    },
    async createList(store, { id, title }) {
      const data = await lists.create(id, title);
      store.commit("newList", data);
    },
    async createCard(store, { listId, ...data }) {
      const card = await cards.create(listId, data);
      store.commit("addCard", { ...card, listId });
    },
    async moveCard(store, { listId, cardId, prevListId }) {
      const card = await cards.move(cardId, listId);
      store.commit("moveCard", { listId, cardId, prevListId, ...card });
    },
    async currentDesk(store, { id }) {
      const desk = await desks.findOne(+id);
      const data = await lists.list(id);

      await Promise.all([
        new Promise((r) => r(store.commit("currentDesk", desk))),
        new Promise((r) => r(store.commit("lists", data))),
      ]);
    },
    async fetchComments(store, { id }) {
      const data = await comments.list(id);
      store.commit("addComments", data);
    },
    async createComment(store, { id, content }) {
      const data = await comments.create(content, id);
      store.commit("addComment", data);
    },
    logout(store) {
      store.commit("setMe");
      localStorage.removeItem("x-token");
    },
  },
  setters: {
    cardModalActive() {},
  },
  modules: {},
});
