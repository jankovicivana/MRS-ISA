<template>
  <nav class="navbar navbar-expand-sm navbar-dark" style="background: #2e6b6b">
      <div class="navbar-brand ml-2" href="javascript:void(0)" style="font-size:25px">
         RentalApp
        <i class="fas fa-anchor" style="color: white;font-size:30px"></i>
      </div>
      <div class="navbar-menu">
        <div class="navbar-start">

          <a class="navbar-item" href="#/client/clientHomepage">Home </a>

          <a class="navbar-item" href="#/client/clientProfile">My profile</a>

          <a class="navbar-item" href="#/client/clientHomepage">Special offers </a>

          <a class="navbar-item" href="#/client/clientHomepage">Subscriptions </a>

          <a class="navbar-item" href="#/client/clientHomepage">Reviews </a>

          <a class="navbar-item" href="#/client/clientHomepage">Complaints </a>


          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link" style="background-color: #2e6b6b"> Reservations </a>
            <div class="navbar-dropdown is-boxed">
              <a class="navbar-item" href="/pastReservations"> Past Reservations </a>
              <a class="navbar-item" href="/upcomingReservations"> Upcoming Reservations </a>
            </div>
          </div>

        </div>
      </div>
      <a href="#/" class="px-3 mr-1" >Log out</a>
  </nav>
</template>

<script>

import axios from "axios";

export default {
  name: "ClientNavbar"
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
