<template>
  <div>
    <main_navbar></main_navbar>
    <homepage></homepage>
  </div>

</template>

<script>
import MainNavbar from "./header/MainNavbar";
import Homepage from "./Homepage";

export default {
  name: "MainHomepage",
  components: {"main_navbar": MainNavbar, "homepage": Homepage}
}
</script>

<style scoped>

</style>
