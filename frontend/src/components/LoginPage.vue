<template>
  <div class="login_main vh-100" >
    <main_navbar></main_navbar>
    <div class="is-flex content my-5 is-justify-content-center" style="height: 100%; margin-bottom: 300px">
      <div class="box p-6 min-width-800" style="height: 370px">
        <h1 class="title">Login to your Account</h1>
        <hr />
        <div class="field">
          <p class="control">
            <input
              class="input"
              type="email"
              placeholder="Email"
              ref="email"
            />
          </p>
        </div>
        <div class="field">
          <p class="control">
            <input
              class="input"
              type="password"
              placeholder="Password"
             ref="password"
            />
          </p>
        </div>
        <div class="field mt-5">
          <p class="control">
            <router-link class="button is-success login-btn" :to="{ name: 'ClientHomepage' }" style="background: #2e6b6b;border: none;color: white;margin: 5px" tag="button">Login</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MainNavbar from "./header/MainNavbar";
import ClientHomepage from "./ClientHomepage";
import router from "../router";

export default {
  name: "LoginPage",
  components: {"main_navbar": MainNavbar},
  methods:{
    login: function(){
    }
  }
}
</script>

<style scoped>

.login_main{
  background-attachment: fixed;
  background-image: linear-gradient(rgba(0, 0, 0, 0.5),
  rgba(0, 0, 0, 0.5)), url('../assets/images/login.jpg') ;
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
  width: 100%;
  object-fit: cover;

}


.login-btn:hover {
  background-color: darkcyan;
  color: white;
}

.login-btn:focus {
  background: darkcyan;
  box-shadow: none
}

</style>
