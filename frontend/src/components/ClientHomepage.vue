<template>
  <div>
    <client_navbar></client_navbar>
    <homepage></homepage>
  </div>

</template>

<script>

import ClientNavbar from "./header/ClientNavbar";
import Homepage from "./Homepage";

export default {
  name: "ClientHomepage",
  components: {'client_navbar': ClientNavbar, "homepage": Homepage}
}
</script>

<style scoped>

</style>
