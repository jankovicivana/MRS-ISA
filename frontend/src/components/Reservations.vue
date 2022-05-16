<template>
  <div class="reservations_main">
    <ClientNavbar></ClientNavbar>
    <div class="container pt-5">
      <h1> Browse available entities </h1>

      <div class="search_div row">
        <div class="column col-2">
          <input class="input" v-model="searchText" type="text" placeholder="Name"/>
        </div>
        <div class="column col-2">
          <select title="Select city" v-model="selectedCity" style="height: 40px; width:200px">
            <option selected value="">Anywhere</option>
            <option v-for="a in this.addresses" :value="a.city">
              {{a.city}}
            </option>
          </select>
        </div>
        <div class="column col-2">
          <input class="input" v-model="price" type="number" placeholder="Max price"/>
        </div>
        <div class="column col-2">
          <input class="input" type="number" placeholder="People" name="numPeople" min="1" max="10" style="height: 40px">
        </div>
      </div>
      <div class="columns">
        <div class="column col-2 ">
          <span style="color: white">Start</span>
          <input type="date" name="startDate" placeholder="start" style="height: 40px; width:200px">
        </div>
        <div class="column col-2">
          <span style="color: white">End</span>
          <input type="date" name="endDate" placeholder="end" style="height: 40px; width:200px">
        </div>
        <div class="column col-2">
          <button type="button" class="btn search_btn" style="float: left">Search</button>
        </div>
      </div>



    </div>
  </div>
</template>

<script>
import ClientNavbar from "./header/ClientNavbar";
import axios from "axios";
export default {
  name: "Reservations",
  components: {ClientNavbar},
  data: function(){
    return{
      addresses: '',
      selectedCity: ''
    }
  },
  mounted: function () {
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/address/all")
      .then(response => (this.addresses = response.data))
  }
}
</script>

<style scoped>

.reservations_main {
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
  padding-bottom: 30px;
  color: white;
}


.search_btn {
  box-shadow: none;
  border: none;
  background-color: #2e6b6b;
  cursor: pointer;
  text-transform: uppercase;
  color: white;
  margin-top: 25px;
  height: 40px;
}

.search_btn:hover {
  background: darkcyan;
}

.search_btn:focus {
  background: darkcyan;
  box-shadow: none
}

.search_btn:active {
  background: darkcyan;
  box-shadow: none
}

a{
  text-decoration: none;
}
</style>
