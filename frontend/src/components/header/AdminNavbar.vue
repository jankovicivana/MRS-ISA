<template>
  <nav class="navbar navbar-expand-sm navbar-dark" style="background: #2e6b6b">
    <div class="navbar-brand ml-2" href="javascript:void(0)" style="font-size:25px">
      RentalApp
      <i class="fas fa-anchor" style="color: white;font-size:30px"></i>
    </div>
    <div class="navbar-menu">
      <div class="navbar-start">

        <a class="navbar-item" href="#/admin/homepage">Home </a>

        <a class="navbar-item" href="#/AdvertiserProfile">My profile</a>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Rental products </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/client/clientHomepage"> Cottages</a>
            <a class="navbar-item" href="#/client/clientHomepage"> Boats </a>
          </div>
        </div>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Owners </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/admin/viewUsers/clients"> Clients</a>
            <a class="navbar-item" href="#/admin/viewUsers/instructors"> Fishing instructors </a>
            <a class="navbar-item" href="#/admin/viewUsers/boatOwners"> Boat owners </a>
            <a class="navbar-item" href="#/admin/viewUsers/cottageOwners"> Cottage owners </a>
            <a class="navbar-item" href="#/admin/AddAdministrator"> Add administrator </a>
          </div>
        </div>


        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Requests </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/admin/registrationRequests"> Registration requests</a>
            <a class="navbar-item" href="#/client/clientHomepage"> Delete account requests </a>
          </div>
        </div>

        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link" style="background-color: #2e6b6b"> Communication </a>
          <div class="navbar-dropdown is-boxed">
            <a class="navbar-item" href="#/admin/revisions"> Client reviews</a>
            <a class="navbar-item" href="#/admin/penaltyView"> Reservation reports</a>
            <a class="navbar-item" href="#/client/clientHomepage"> Complaints </a>
          </div>
        </div>


      </div>
    </div>
    <a href="#/" class="px-3 mr-1" >Log out</a>
  </nav>
</template>

<script>
export default {
  name: "AdminNavbar"
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
