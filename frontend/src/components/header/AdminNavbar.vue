<template>
  <nav class="navbar navbar-expand-sm navbar-dark" style="background: #2e6b6b">
    <div class="navbar-brand ml-2" href="javascript:void(0)" style="font-size:25px">
      RentalApp
      <i class="fas fa-anchor" style="color: white;font-size:30px"></i>
    </div>
    <div class="navbar-menu">
      <div class="navbar-start">

        <a class="navbar-item" href="#/admin/homepage">Home </a>

        <router-link class="navbar-item" :to="{name: 'AdvertiserProfile', params: { isAdmin: true }}">My profile</router-link>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Rental products </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/cottages"> Cottages</a>
            <a class="navbar-item" href="#/boats"> Boats </a>
            <a class="navbar-item" href="#/adventures"> Adventures </a>
          </div>
        </div>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Owners </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/admin/viewUsers/clients"> Clients</a>
            <a class="navbar-item" href="#/admin/viewUsers/instructors"> Fishing instructors </a>
            <a class="navbar-item" href="#/admin/viewUsers/boatOwners"> Boat owners </a>
            <a class="navbar-item" href="#/admin/viewUsers/cottageOwners"> Cottage owners </a>
            <a v-if="this.admin.isPredefined" class="navbar-item" href="#/admin/AddAdministrator"> Add administrator </a>
          </div>
        </div>


        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Requests </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/admin/registrationRequests"> Registration requests</a>
            <a class="navbar-item" href="#/admin/deleteProfileRequests"> Delete account requests </a>
          </div>
        </div>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Communication </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/admin/revisions"> Client reviews</a>
            <a class="navbar-item" href="#/admin/penaltyView"> Reservation reports</a>
            <a class="navbar-item" href="#/admin/complaints"> Complaints </a>
          </div>
        </div>
        <a class="navbar-item" href="#/businessReport">Business report </a>
        <a class="navbar-item" href="#/admin/loyalty">Loyalty program </a>

      </div>
    </div>
    <a href="#/" v-on:click="logout()" class="px-3 mr-1" >Log out</a>
  </nav>
</template>

<script>
import router from "../../router";
import axios from "axios";

export default {
  name: "AdminNavbar",
  data: function(){
    return{
      admin: ''
    }
  },
  methods: {
    logout: function (){
      sessionStorage.clear();
      router.push('/');
    }
  }
  ,mounted() {
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/administrator/getAdmin", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.admin = response.data)).catch(function error(error) {
      alert(error.response.data);
    });
  }

}
</script>

<style scoped>
a {
  color: white;
  text-decoration: none;
  border-style: none;
}

a:focus {
  color: white;
  text-decoration: none;
  border-style: none;
}

a.navbar-item:visited{
  background-color: #2e6b6b;
  text-decoration: none;
  border-style: none;
}

a.navbar-item, .navbar-link {
  cursor: pointer;
}

a:hover {
  color: grey;
}

.navbar-item:hover{
  background-color: #2e6b6b;
}

.navbar-link:not(.is-arrowless)::after {
  border-color: white;
  margin-top: -0.375em;
  right: 1.125em;
}

.navbar-dropdown{
  background-color: #2e6b6b;
}

a.navbar-item.is-active{
  color: grey;
}

</style>
