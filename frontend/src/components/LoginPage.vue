<template>
  <div class="login_main vh-100 is-fullheight" >
    <main_navbar></main_navbar>
    <div class="is-flex mt-5 is-justify-content-center">
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
  data: function (){
    return{
      admin: '',
      changed:''
    }
  },
  components: {"main_navbar": MainNavbar},
  methods:{
    show: function (group, type = '',title, text) {
      this.$notify({group, title, text, type})
    },

    login: function(){
      let username = this.$refs.email.value;
      let password = this.$refs.password.value;
      this.info = {username, password}
      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/auth/login", this.info)
        .then(response => {
          if(response.data === ""){
            this.show('foo-css', 'error',`<p style="font-size: 25px">Not activated</p>`,`<p style="font-size: 20px">You must activate your account first</p>`)
          } else{
            this.loginSuccessful(response);
          }
        })
        .catch((error) => { this.loginFailed(); })
    },
  loginSuccessful: function(response){
    sessionStorage.setItem("accessToken", response.data.accessToken);
    sessionStorage.setItem("expiresIn", response.data.expiresIn);
    sessionStorage.setItem("role", response.data.role);

    if(response.data.role === "ROLE_client"){
      router.push('/client/clientHomepage');
    } else if (response.data.role === "ROLE_admin") {
      this.findAdmin();
    } else if (response.data.role === "ROLE_cottageOwner") {
      router.push('/cottageOwner/cottageOwnerHomepage');
    } else if (response.data.role === "ROLE_boatOwner") {
      router.push('/boatOwner/boatOwnerHomepage');
    } else if (response.data.role === "ROLE_fishingInstructor") {
      router.push('/fishingInstructor/instructorHomepage');
    }
  },
    findAdmin:function (){
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/administrator/getAdmin", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
        if (response.data.passwordChanged===true)
        {
          router.push('/admin/homepage');
        }else{
          router.push('/user/passwordChange');
        }})
    },

    loginFailed(error){
      this.show('foo-css', 'error',`<p style="font-size: 25px">Wrong credentials</p>`,`<p style="font-size: 20px">Wrong username or password</p>`)
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
  background-repeat: no-repeat;
  background-size: cover;
  height: fit-content;
  opacity: 1;
  top: 0;
  transition: opacity 0.3s linear 0s;
  width: 100%;
  overflow: visible;
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
