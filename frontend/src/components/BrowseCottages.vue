<template>
  <div class="browse_main">
    <main_navbar></main_navbar>
    <div class="mt-5 container">
      <h1>Cottages</h1>
      <hr style="color: #2e6b6b"/>
      <div>
        <div>
          pretraga
        </div>
        <div v-if="cottages.length === 0">
          <p style="color: white"> No cottages for now.</p>
        </div>
        <div v-for="c in cottages">
          <browse_card :cottage="c"></browse_card>
        </div>
      </div>
    </div>
    <div style="height: 150px"></div>
  </div>
</template>

<script>
import axios from "axios";
import MainNavbar from "./header/MainNavbar";
import CottageBrowseCard from "./CottageBrowseCard";
export default {
  name: "Cottages",
  components: {'browse_card': CottageBrowseCard, 'main_navbar': MainNavbar},
  data: function(){
    return{
      cottages: ''
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/all")
      .then(response => (this.cottages = response.data))
  },
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
