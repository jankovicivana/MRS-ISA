<template>
  <div>
  <AdminNavbar :isAdmin="true"></AdminNavbar>
  <section class="hero is-fullheight-with-navbar main_homepage">
    <div class="hero-body">
      <div class="container has-text-centered ml-4 pl-4">
        <h1 class="title">Welcome administrator!</h1>
        <p class="text">Manage rental app.</p>
      </div>
    </div>
  </section>

  </div>
</template>

<script>
import AdminNavbar from "./header/AdminNavbar";
export default {
  name: "AdminHomepage",
  components: {AdminNavbar}
}
</script>

<style scoped>
.main_homepage {
  background-attachment: fixed;
  background-image: url('../assets/images/back1.jpg');
  background-position-y: 0;
  background-repeat: no-repeat;
  background-size: cover;
  opacity: 1;
  top: 0;
  transition: opacity 0.3s linear 0s;
  width: 100%;
  box-sizing: border-box;

}

* {
  margin: 0;
  box-sizing: border-box;
}

.card_image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-image {
  height: 250px;
}

.card {
  cursor: pointer;
  width: 300px;
  transition: transform .2s;
}

.card:hover{
  transform: scale(1.08);
}

a{
  text-decoration: none;
  color: white;
}

h1{
  color: white;
  font-size: xxx-large;
  font-weight: bold;
}

.text{
  color: white;
  font-size: xx-large;
}

.view_button{
  background-color: #4AAE9B;
  color: white;
}

.view_button:hover{
  background-color: #2e6b6b;
}

h2 {
  text-align: center;
}

</style>
