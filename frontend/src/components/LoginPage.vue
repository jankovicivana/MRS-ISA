<template>
  <div class="login_main vh-100" >
    <main_navbar></main_navbar>
    <div class="is-flex content my-5 is-justify-content-center" style="height: 100%;">
      <div class="box p-6" style="height: 400px; width: 400px">
        <h1 class="title justify-content-center is-flex">Login</h1>
        <hr/>
        <div>
          <p>
            <input class="input" type="email" placeholder="Email" ref="email"/>
          </p>
        </div>
        <div>
          <p>
            <input class="input" type="password" placeholder="Password" ref="password"/>
          </p>
        </div>

        <div class="mt-3 justify-content-center align-items-center is-flex">
          <p class="justify-content-center">
            <button class="button is-success login-btn" v-on:click="login" style="background: #2e6b6b;border: none;color: white;margin: 5px" tag="button">Login</button>
          </p>
        </div>
        <div class="mt-3 ml-1 mb-2">
          <a href="">Register</a> if you don't have an account yet.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MainNavbar from "./header/MainNavbar";
import axios from "axios";
import router from "../router";

export default {
  name: "LoginPage",
  components: {"main_navbar": MainNavbar},
  methods:{
    login: function(){
      let username = this.$refs.email.value;
      let password = this.$refs.password.value;
      this.info = {username, password}
      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/auth/login", this.info)
        .then(response => { this.loginSuccessful(response);})
        .catch((error) => { this.loginFailed(); })
    },

    loginSuccessful: function(response){
        sessionStorage.setItem("accessToken", response.data.accessToken);
        sessionStorage.setItem("expiresIn", response.data.expiresIn);
        sessionStorage.setItem("role", response.data.role);

        if(response.data.role === "ROLE_client"){
          router.push('/client/clientHomepage');
        } else if (response.data.role === "ROLE_admin") {
          router.push('/admin/AddAdministrator');
        } else if (response.data.role === "ROLE_cottageOwner") {
          router.push('/cottageOwner/cottageOwnerHomepage');
        } else if (response.data.role === "ROLE_boatOwner") {
          router.push('/boatOwner/boatOwnerHomepage');
        } else if (response.data.role === "ROLE_fishingInstructor") {
          router.push('/fishingInstructor/instructorHomepage');
        }
    },

    loginFailed(){
      this.error = 'Login failed!';
      delete localStorage.token
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
