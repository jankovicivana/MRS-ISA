<template>
  <div class="browse_main">
    <main_navbar></main_navbar>
    <div class="mt-5 container">
      <h1>Adventures</h1>
      <hr style="color: #2e6b6b"/>
      <div>
        <div>
          pretraga
        </div>
        <div v-if="adventures.length === 0">
          <p style="color: white"> No adventures  for now.</p>
        </div>
        <div v-for="a in adventures">
          <browse_card :adventure="a"></browse_card>
        </div>
      </div>
    </div>
    <div style="height: 150px"></div>
  </div>
</template>

<script>
import MainNavbar from "./header/MainNavbar";
import AdventureBrowseCard from "./AdventureBrowseCard";
import axios from "axios";

export default {
  name: "BrowseAdventures",
  components: {'main_navbar': MainNavbar, 'browse_card': AdventureBrowseCard},
  data: function (){
    return {
      adventures: ''
    }
  },
  mounted: function(){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/all")
      .then(response => (this.adventures = response.data))
  }
}
</script>

<style scoped>

.browse_main {
  background-attachment: fixed;
  background-image: url('../assets/images/back2.jpg');
  background-position-y: 0;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  opacity: 1;
  position: absolute;
  top: 0;
  transition: opacity 0.3s linear 0s;
  width: 100%;
  overflow: scroll;
}

h1{
  color: white;
}
</style>
