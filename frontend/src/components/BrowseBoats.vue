<template>
  <div class="browse_main">
    <main_navbar></main_navbar>
    <div class="mt-5 container">
      <h1>Boats</h1>
      <hr style="color: #2e6b6b"/>
      <div>
        <div>
          pretraga
        </div>
        <div v-if="boats.length === 0">
          <p style="color: white"> No boats for now.</p>
        </div>
        <div v-for="b in boats">
          <browse_card :boat="b"></browse_card>
        </div>
      </div>
    </div>
    <div style="height: 150px"></div>
  </div>
</template>

<script>
import MainNavbar from "./header/MainNavbar";
import BoatBrowseCard from "./BoatBrowseCard";
import axios from "axios";

export default {
  name: "BrowseBoats",
  components: {'browse_card': BoatBrowseCard, 'main_navbar': MainNavbar},
  data: function(){
    return{
      boats: ''
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/boats/all")
      .then(response => (this.boats = response.data))
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
