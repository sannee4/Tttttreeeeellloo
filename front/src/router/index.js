import Vue from "vue";
import VueRouter from "vue-router";
import Register from "../views/Register.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/Login.vue"),
  },
  {
    path: "/",
    name: "Boards",
    component: () => import("../views/Boards.vue"),
  },
  {
    path: "/create-board",
    name: "CreateBoard",
    component: () => import("../views/CreateBoard.vue"),
  },
  {
    path: "/:id",
    name: "Board",
    component: () => import("../views/Board.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
