<template>
  <div class="container">
    <router-link class="container create-board-link" to="/create-board" tag="div">+ Create Board</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return { isComponentModalActive: false };
  }
};
</script>

<style>
.create-board-link {
  width: 100%;
  background: #d3d3d3;
  padding: 20px 0;
  cursor: pointer;
}
</style>