<template>
  <nav class="navbar navbar-expand-sm navbar-dark" style="background: #2e6b6b">
    <div class="navbar-brand ml-2" href="javascript:void(0)" style="font-size:25px">
      RentalApp
      <i class="fas fa-anchor" style="color: white;font-size:30px"></i>
    </div>
    <div class="navbar-menu">
      <div class="navbar-start">
        <a class="navbar-item" href="#/">Home </a>
        <a class="navbar-item" href="#/">Special offers </a>
      </div>
    </div>
    <a href="#/">Register</a>
    <a href="#/login" class="px-3 mr-1" >Log in</a>
  </nav>
</template>

<script>
export default {
  name: "MainNavbar"
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


a.navbar-item.is-active{
  color: grey;
}
</style>
