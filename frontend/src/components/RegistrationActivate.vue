<template>
  <div>
    <MainNavbar></MainNavbar>
    <section class="hero is-fullheight-with-navbar main_homepage">
      <div class="hero-body">
        <div class="container has-text-centered ml-4 pl-4">
          <h1 v-if="success" class="title">Activated!</h1>
          <p v-if="success" class="text">Your account has been activated and you can now log in.</p>
          <h1 v-else class="title">Activation failed.</h1>
        </div>
      </div>
    </section>
  </div>

</template>

<script>
import axios from "axios";
import MainNavbar from "./header/MainNavbar";

export default {
  name: "RegistrationActivate",
  components: {MainNavbar},
  data: function(){
    return {
      success: ''
    }
  },
  props: {
    token: {
      required: true,
    },
  },

  mounted: function (){
    axios
      .get("https://rental-app-6.herokuapp.com" + "/api/clients/activate/" + this.token,  {
        headers: {
          Authorization: "Bearer " + this.token
        }
      }).then(response => { this.success = true;}).catch(error => { this.success = false
          if(error.response.status === 400){
            this.show('foo-css', 'warning',`<p style="font-size: 25px">Already activated</p>`,`<p style="font-size: 20px">You have already activated your account</p>`)

          } else if(error.response.status === 401){
            this.show('foo-css', 'error',`<p style="font-size: 25px">Activation failed</p>`,`<p style="font-size: 20px">Your activation link is invalid or has expired</p>`)

          }
          else{
            this.show('foo-css', 'error',`<p style="font-size: 25px">Activation failed</p>`,`<p style="font-size: 20px">User doesn't exist</p>`)
          }
      })
  },
  methods: {
    show: function (group, type = '',title, text) {
      this.$notify({group, title, text, type})
    },
  }
}
</script>

<style scoped>
.main_homepage {
  background-attachment: fixed;
  background-image: url('../assets/images/back2.jpg');
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


h1{
  color: white;
  font-size: xxx-large;
  font-weight: bold;
}

.text{
  color: white;
  font-size: xx-large;
}

</style>
