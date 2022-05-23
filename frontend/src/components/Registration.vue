<template>
  <div class="register_main">
    <MainNavbar></MainNavbar>
    <div class="is-flex content is-justify-content-center" style="height: 100%;">
      <div class="box p-6 mt-5 mb-5" >
        <h1 class="title justify-content-left is-flex">Create an account</h1>
        <hr/>
        <div class="columns">
          <div class="column">
            <span>Name</span>
            <p><input required class="input" placeholder="Name" ref="name"/></p>
          </div>
          <div class="column">
            <span>Surname</span>
            <p><input required class="input" placeholder="Surname" ref="surname"/></p>
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <span>Country</span>
            <p><input required class="input" placeholder="Country" ref="country"/></p>
          </div>

          <div class="column">
            <span>City</span>
            <p><input required class="input" placeholder="City" ref="city"/></p>
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <span>Street</span>
            <p><input required class="input" placeholder="Street" ref="street"/></p>
          </div>
          <div class="column">
            <span>Postal code</span>
            <p><input type="number" required class="input" ref="postalCode"/></p>
          </div>
        </div>

        <div>
          <span>Phone number</span>
          <p><input required class="input" placeholder="Phone number" ref="phoneNumber"/></p>
        </div>

        <div>
          <span>Email</span>
          <p><input required class="input" type="email" placeholder="Email" ref="email"/></p>
        </div>
        <div>
          <span>Password</span>
          <p><input required class="input" type="password" placeholder="Password" ref="password"/></p>
        </div>
        <div>
          <span>Confirm password</span>
          <p><input required class="input" type="password" placeholder="Confirm password" ref="confirmPassword"/></p>
        </div>

        <div class="mt-3 justify-content-center align-items-center is-flex">
          <p class="justify-content-center">
            <button class="button is-success register-btn" v-on:click="register">Submit</button>
          </p>
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
  name: "Registration",
  components: {MainNavbar},

  methods: {
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfull registration</p>`
      let text = `<p style="font-size: 20px">Successfully created an account!</p>`
      this.$notify({group, title, text, type})
    },

    register: function () {
      let email = this.$refs.email.value;
      let password = this.$refs.password.value;
      let name = this.$refs.name.value;
      let surname = this.$refs.surname.value;
      let city = this.$refs.city.value;
      let country = this.$refs.country.value;
      let street = this.$refs.street.value;
      let postalCode = this.$refs.postalCode.value;
      let role = "ROLE_client";
      let phoneNumber = this.$refs.phoneNumber.value;
      this.info = {email, password, name, surname, city, country, street, postalCode, role, phoneNumber};

      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/auth/register", this.info)
        .then(response => { this.registerSuccessful(response);})
        .catch((error) => { this.registerFailed(); })
    },

    registerSuccessful: function(response){
      this.show('foo-css', 'success');
      setTimeout(() => { router.push('/login'); }, 1500);
    },

    registerFailed: function(){
      alert("Neuspjesna registracija");
    }
  }
}
</script>

<style scoped>

.register_main{
  background-attachment: fixed;
  background-image: linear-gradient(rgba(0, 0, 0, 0.5),
  rgba(0, 0, 0, 0.5)), url('../assets/images/login.jpg') ;
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.register-btn{
  background: #2e6b6b;
  color: white;
}

.register-btn:hover {
  background-color: darkcyan;
  color: white;
}
.register-btn:focus {
  background: darkcyan;
  box-shadow: none
}

.box{
  width: 45%;
  height: 80%;
  margin-bottom: 30px;
}
</style>
