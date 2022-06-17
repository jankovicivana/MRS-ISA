<template>
  <div class="reservations_main">
    <CottageOwnerNavbar v-if="role === 'ROLE_cottageOwner'"></CottageOwnerNavbar>
    <ClientNavbar v-if="role === 'ROLE_client'"></ClientNavbar>
    <div class="container pt-5">
      <h1> Browse available entities </h1>
      <div class="search_div row">
        <div class="column col-2">
          <span style="color: white "  v-if="role !== 'ROLE_client'">Name</span>
          <input v-if="role !== 'ROLE_client'" class="input" ref="name"  type="text"  placeholder="Name"/>
          <span style="color: white "  v-if="role === 'ROLE_client'">Type</span>
          <select  v-if="role === 'ROLE_client'" ref="type" v-model="type" style="height: 40px; width:200px">
            <option>Cottage</option>
            <option>Boat</option>
            <option>Adventure</option>
          </select>
        </div>
        <div class="column col-2">
          <span style="color: white">Location</span>
          <select title="Select city" ref="location" style="height: 40px; width:200px">
            <option v-for="a in this.addresses" :value="a.city">
              {{a.city}}
            </option>
          </select>
        </div>
        <div class="column col-2">
          <span style="color: white">Price</span>
          <input class="input" ref="price" value="500" type="number" min="0" placeholder="Max price"/>
        </div>
        <div class="column col-2">
          <span style="color: white">People</span>
          <input class="input" type="number" value="4" placeholder="Number of people" ref="people" min="1" max="10" style="height: 40px">
        </div>
      </div>
      <div class="columns">
        <div class="column col-2 ">
          <span style="color: white">Start date</span>
          <input type="date" name="startDate" ref="startDate" value="2022-06-26" style="height: 40px; width:200px">
        </div>
        <div class="column col-2 ">
          <span style="color: white">Start time</span>
          <input type="time" name="startTime" ref="startTime" value="10:00:00" style="height: 40px; width:200px">
        </div>

        <div class="column col-2">
          <span style="color: white">End date</span>
          <input type="date" name="endDate" ref="endDate" value="2022-06-26" style="height: 40px; width:200px">
        </div>
        <div class="column col-2">
          <span style="color: white">End time</span>
          <input type="time" name="endTime" ref="endTime"value="10:00:00"  style="height: 40px; width:200px">
        </div>


        <div class="column col-2">
          <span style="color: white">Rating</span>
          <star-rating :rating="4" :read-only="false" :increment="1" :show-rating="false"  @rating-selected="setRating" :star-size="35" :size="300" style="height: 40px"></star-rating>
        </div>
        <div class="column col-2">
          <button type="submit" v-on:click="search()" class="btn search_btn" style="float: left">Search</button>
        </div>
      </div>

      <div v-if="entities.length === 0">
        <p style="color: white"> Nothing to show for now.</p>
      </div>
      <div v-for="e in entities">
        <browse_card :cottage="e" :canReserve="true"  v-on:reserve="reserve($event)"></browse_card>
      </div>

    </div>

  </div>
</template>

<script>
import ClientNavbar from "./header/ClientNavbar";
import axios from "axios";
import CottageBrowseCard from "./CottageBrowseCard";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
export default {
  name: "Reservations",
  components: {CottageOwnerNavbar, ClientNavbar, 'browse_card': CottageBrowseCard},
  data: function(){
    return{
      addresses: '',
      entities: '',
      params: '',
      type: '',
      rating: 3,
      role:'',
      clientId: this.$route.params.clientId,
    }
  },
  mounted: function () {
    this.role = sessionStorage.getItem("role");
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/address/all")
      .then(response => (this.addresses = response.data));
    this.type = "Cottage";
  },
  methods: {
    search: function (){
      if(this.role === 'ROLE_client'){
        this.params = {type: this.$refs.type.value, city: this.$refs.location.value, price: this.$refs.price.value, people: this.$refs.people.value,
          startDate: this.$refs.startDate.value, startTime: this.$refs.startTime.value, endDate: this.$refs.endDate.value, endTime: this.$refs.endTime.value, rating: this.rating};
        axios
          .post(process.env.VUE_APP_SERVER_PORT+"/api/entity/getAvailable", this.params,{
            headers: {
              Authorization:
                'Bearer ' + sessionStorage.getItem("accessToken")
            }})
          .then(response => (this.entities = response.data)).catch(function error(error) {
          alert(error.response.data);});
      }else if(this.role === 'ROLE_cottageOwner'){
        this.params = {name: this.$refs.name.value, city: this.$refs.location.value, price: this.$refs.price.value, people: this.$refs.people.value,
          startDate: this.$refs.startDate.value, startTime: this.$refs.startTime.value, endDate: this.$refs.endDate.value, endTime: this.$refs.endTime.value, rating: this.rating};
        axios
          .post(process.env.VUE_APP_SERVER_PORT+"/api/cottages/getAvailable", this.params,{
            headers: {
              Authorization:
                'Bearer ' + sessionStorage.getItem("accessToken")
            }})
          .then(response => (this.entities = response.data)).catch(function error(error) {
          alert(error.response.data);});
      }

    },
    show: function(group, type='', entityType){
      let title = `<p style="font-size: 25px">Reserved!</p>`
      let text = `<p style="font-size: 20px">Successfully reserved ` + entityType + `!</p>`
      this.$notify({group, title, text, type})
    },
    setRating: function (rating){
      this.rating = rating;
    },
    reserve: function (entity){
      var data = {personNum: this.$refs.people.value, startDate: this.$refs.startDate.value, startTime: this.$refs.startTime.value,
        endDate: this.$refs.endDate.value, endTime: this.$refs.endTime.value, entityId: entity.id,clientId:this.clientId}
      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/reservation/reserve", data,{
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }})
        .then(response => {this.show('foo-css', 'success', entity.type); setTimeout(() => {location.reload(); }, 1500) }).catch(function error(error) {
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
  overflow: visible;
}
h1{
  padding-bottom: 30px;
  color: white;
}
button {
  box-shadow: none;
  border: none;
  background-color: #2e6b6b;
  cursor: pointer;
  text-transform: uppercase;
  color: white;
  margin-top: 25px;
  height: 40px;
}
button:hover {
  background: darkcyan;
}
button:focus {
  background: darkcyan;
  box-shadow: none
}
button:active {
  background: darkcyan;
  box-shadow: none
}
a{
  text-decoration: none;
}
star-rating{
  align-self: normal;
}
</style>
