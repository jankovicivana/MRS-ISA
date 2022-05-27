<template>
  <div class="reservations_main">
    <ClientNavbar></ClientNavbar>
    <div class="container pt-5">
      <h1> Browse available entities </h1>
      <div class="search_div row">
        <div class="column col-2">
          <span style="color: white">Name</span>
          <input class="input" ref="name" type="text" placeholder="Name"/>
        </div>
        <div class="column col-2">
          <span style="color: white">Location</span>
          <select title="Select city" ref="location" style="height: 40px; width:200px">
            <option selected value="">Anywhere</option>
            <option v-for="a in this.addresses" :value="a.city">
              {{a.city}}
            </option>
          </select>
        </div>
        <div class="column col-2">
          <span style="color: white">Price</span>
          <input class="input" ref="price" type="number" min="0" placeholder="Max price"/>
        </div>
        <div class="column col-2">
          <span style="color: white">People</span>
          <input class="input" type="number" placeholder="Number of people" ref="people" min="1" max="10" style="height: 40px">
        </div>
      </div>
      <div class="columns">
        <div class="column col-2 ">
          <span style="color: white">Start date</span>
          <input type="date" name="startDate" ref="startDate" placeholder="start" style="height: 40px; width:200px">
        </div>
        <div class="column col-2 ">
          <span style="color: white">Start time</span>
          <input type="time" name="startTime" ref="startTime" placeholder="start" style="height: 40px; width:200px">
        </div>

        <div class="column col-2">
          <span style="color: white">End date</span>
          <input type="date" name="endDate" ref="endDate" placeholder="end" style="height: 40px; width:200px">
        </div>
        <div class="column col-2">
          <span style="color: white">End time</span>
          <input type="time" name="endTime" ref="endTime" placeholder="end" style="height: 40px; width:200px">
        </div>


        <div class="column col-2">
          <span style="color: white">Rating</span>
          <input class="input" type="number" placeholder="Rating" ref="rating" min="0" max="5" style="height: 40px">
        </div>
        <div class="column col-2">
          <button type="submit" v-on:click="search()" class="btn search_btn" style="float: left">Search</button>
        </div>
      </div>

      <div v-if="entities.length === 0">
        <p style="color: white"> Nothing to show for now.</p>
      </div>
      <div v-for="e in entities">
        <browse_card :cottage="e"></browse_card>
      </div>

    </div>

  </div>
</template>

<script>
import ClientNavbar from "./header/ClientNavbar";
import axios from "axios";
import CottageBrowseCard from "./CottageBrowseCard";
export default {
  name: "Reservations",
  components: {ClientNavbar, 'browse_card': CottageBrowseCard},
  data: function(){
    return{
      addresses: '',
      entities: '',
      params: ''
    }
  },
  mounted: function () {
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/address/all")
      .then(response => (this.addresses = response.data))
  },
  methods: {
    search: function (){
      this.params = {name: this.$refs.name.value, city: this.$refs.location.value, price: this.$refs.price.value, people: this.$refs.people.value,
        startDate: this.$refs.startDate.value, startTime: this.$refs.startTime.value, endDate: this.$refs.endDate.value, endTime: this.$refs.endTime.value, rating: this.$refs.rating.value};
      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/entity/getAvailable", this.params,{
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }})
        .then(response => (this.entities = response.data)).catch(function error(error) {
        alert(error.response.data);});
    }
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
