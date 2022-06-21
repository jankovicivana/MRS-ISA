<template>
  <div class="browse_main is-fullheight min-vh-100">
    <main_navbar v-if="role === null"></main_navbar>
    <fishing-instructor-navbar v-if="role === 'ROLE_fishingInstructor'"></fishing-instructor-navbar>
    <client-navbar v-if="role === 'ROLE_client'"></client-navbar>
    <AdminNavbar v-if="role === 'ROLE_admin'" :isAdmin="true"></AdminNavbar>
    <div class="mt-5 container">
      <h1>Adventures</h1>
      <hr style="color: #2e6b6b"/>
      <div>
        <div>
          <div class="columns mb-6">
            <div class="column col-2">
              <span style="color: white">Name</span>
              <input class="input is-link" type="text" placeholder="Name" v-model="searchText"/>
            </div>

            <div class="column col-2">
              <span style="color: white">Price</span>
              <input class="input" ref="price" value="500" type="number" min="0" placeholder="Max price"/>
            </div>

            <div class="column col-2">
              <span style="color: white">Sorting</span>
              <div class="select">
                <select title="Sorting" v-model="searchSort">
                  <option selected="selected" value="NO_SORT" >No sorting</option>
                  <option value="PRICE_ASC">Price: Ascending</option>
                  <option value="PRICE_DESC">Price: Descending</option>
                  <option value="RATING_ASC">Rating: Ascending</option>
                  <option value="RATING_DESC">Rating: Descending</option>
                  <option value="NAME_ASC">Name: Ascending</option>
                  <option value="NAME_DESC">Name: Descending</option>
                  <option value="ADDRESS_ASC">Address: Ascending</option>
                  <option value="ADDRESS_DESC">Address: Descending</option>
                </select>
              </div>
            </div>

            <div class="column col-2">
              <span style="color: white">Rating</span>
              <star-rating :rating="3" :read-only="false" :increment="0.1" :show-rating="false"  @rating-selected="setRating" :star-size="35" :size="300" style="height: 40px" ></star-rating>
            </div>

            <div class="column col-2">
              <button type="button"class="button search_button is-link"  v-on:click="search()">Search</button>
            </div>
          </div>
        </div>
        <div v-if="adventures.length === 0">
          <p style="color: white"> No adventures  for now.</p>
        </div>
        <div v-for="a in search_adventures">
          <browse_card :adventure="a" v-on:deleteAdventure="deleteAdventure($event)" v-on:subscribe="subscribe($event)"></browse_card>
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
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";
import ClientNavbar from "./header/ClientNavbar";
import AdminNavbar from "./header/AdminNavbar";
import _orderBy from 'lodash/orderBy';


export default {
  name: "BrowseAdventures",
  components: {AdminNavbar, ClientNavbar, FishingInstructorNavbar, 'main_navbar': MainNavbar, 'browse_card': AdventureBrowseCard},
  data: function (){
    return {
      adventures: '',
      searchText: '',
      searchSort: "NO_SORT",
      search_adventures: '',
      role:'',
      rating: 3
    }
  },
  mounted: function(){
    this.role = sessionStorage.getItem("role");

    if(this.role === 'ROLE_fishingInstructor'){
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/allByOwner", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.adventures = this.search_adventures = response.data))

    } else if (this.role === 'ROLE_admin'){
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/all", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.adventures = this.search_adventures = response.data))
    }
    else{
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/allByOwner/" + this.$route.params.id)
      .then(response => {this.adventures = this.search_adventures = response.data})
    }},
  methods: {

    show: function (group, type = '',title, text) {
      this.$notify({group, title, text, type})
    },

    deleteAdventure:function (id){
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/adventures/deleteAdventure/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully deleted!</p>`,`<p style="font-size: 20px">Successfully deleted adventure!</p>`)
          setTimeout(() => {location.reload(); }, 3000)
        }).catch((error) => {
        this.show('foo-css', 'error',`<p style="font-size: 25px">Deletion is not possible!</p>`,`<p style="font-size: 20px">Adventure has reservations.</p>`)
      });

    },


    subscribe: function (id){
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/sub/subscribe/"+id, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          if (response.data === "Already subscribed"){
            this.show('foo-css', 'warning',`<p style="font-size: 25px">Subscribed</p>`,`<p style="font-size: 20px">You are already subscribed!</p>`)
          } else{
            this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully subscribed!</p>`,`<p style="font-size: 20px">You will now receive email notifications about special offers.</p>`)

          }
        });
    },

    setRating: function (rating){
      this.rating = rating;
    },

    search: function (){
      if(this.searchText){
        this.search_adventures = this.adventures.filter(item => {
          return item.name.toLowerCase().includes(this.searchText.toLowerCase());
        })
      }
      else{
        this.search_adventures = this.adventures;
      }

      this.search_adventures = this.search_adventures.filter(item => {
        return item.price <= this.$refs.price.value && item.averageGrade >= this.rating
      })

      if(this.searchSort !== "NO_SORT") {
        if (this.searchSort === "NAME_ASC") {
          this.search_adventures = _orderBy(this.search_adventures, 'name', 'asc')
        } else if (this.searchSort === "NAME_DESC") {
          this.search_adventures = _orderBy(this.search_adventures, 'name', 'desc')
        } else if (this.searchSort === "PRICE_ASC") {
          this.search_adventures = _orderBy(this.search_adventures, 'price', 'asc')
        } else if (this.searchSort === "PRICE_DESC") {
          this.search_adventures = _orderBy(this.search_adventures, 'price', 'desc')
        } else if (this.searchSort === "RATING_ASC") {
          this.search_adventures = _orderBy(this.search_adventures, 'averageGrade', 'asc')
        } else if (this.searchSort === "RATING_DESC") {
          this.search_adventures = _orderBy(this.search_adventures, 'averageGrade', 'desc')
        } else if (this.searchSort === "ADDRESS_ASC") {
          this.search_adventures = _orderBy(this.search_adventures, 'address.street', 'asc')
        } else if (this.searchSort === "ADDRESS_DESC") {
          this.search_adventures = _orderBy(this.search_adventures, 'address.street', 'desc')
        }
      }
    }
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
  height: fit-content;
  opacity: 1;
  position: absolute;
  top: 0;
  transition: opacity 0.3s linear 0s;
  width: 100%;
  overflow: visible;
}

h1{
  color: white;
}


.search_button{
  box-shadow: none;
  border: none;
  background-color: #2e6b6b;
  cursor: pointer;
  text-transform: uppercase;
  color: white;
  margin-top: 25px;
  height: 40px;
}

.search_button:hover{
  background-color: #4AAE9B;
}


star-rating{
  align-self: normal;
}

</style>
