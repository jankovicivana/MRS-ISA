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

        <div class="columns">
          <div class="column">
            <span>Phone number</span>
            <p><input required class="input" placeholder="Phone number" ref="phoneNumber"/></p>
          </div>
          <div class="column">
            <span>User type</span>
            <p><select id="user_type_input" ref="user_type_input" class="form-select" v-on:change="disableFields()" >
              <option>client</option>
              <option>fishing instructor</option>
              <option>boat owner</option>
              <option>cottage owner</option>
            </select></p>
          </div>
        </div>
        <hr />
        <div class="columns">
          <div class="column">
            <span>Email</span>
            <p><input required class="input" type="email" placeholder="Email" ref="email"/></p>
          </div>
        </div>


        <div class="columns">
          <div class="column">
            <span>Password</span>
            <p><input required class="input" type="password" placeholder="Password" ref="password"/></p>
          </div>
          <div class="column">
            <span>Confirm password</span>
            <p><input required class="input" type="password" placeholder="Confirm password" ref="confirmPassword"/></p>
          </div>
        </div>

        <div class="columns">
          <div class="column">
            <span>Reason for registration</span>
            <textarea disabled="true" type="text" id="reason_input" ref="reason_input" class="textarea" required></textarea>
          </div>
          <div class="column">
            <span>Biography</span>
            <textarea disabled="true" type="text" id="biography_input" ref="biography_input" class="textarea" required></textarea>
          </div>
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

    show: function (group, type = '',title, text) {
      this.$notify({group, title, text, type})
    },

    disableFields: function (){
      let user_type = this.$refs.user_type_input.value;
      if(user_type === 'client'){
        document.getElementById('reason_input').disabled = true;
        document.getElementById('biography_input').disabled = true;
      }else if(user_type === 'fishing instructor'){
        document.getElementById('reason_input').disabled = false;
        document.getElementById('biography_input').disabled = false;
      }else{
        document.getElementById('reason_input').disabled = false;
        document.getElementById('biography_input').disabled = true;
      }

    },
    register: function () {

      let name = this.$refs.name.value;
      let surname = this.$refs.surname.value;
      let city = this.$refs.city.value;
      let country = this.$refs.country.value;
      let street = this.$refs.street.value;
      let postalCode = this.$refs.postalCode.value;
      let role;
      let phoneNumber = this.$refs.phoneNumber.value;

      if(name === '' || surname === '' || city === '' || country === '' || street === '' || postalCode === '' || phoneNumber === ''){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Incomplete data</p>`,`<p style="font-size: 20px">You must enter all the info</p>`)
        return
      }

      let email = this.$refs.email.value;
      if(email === '' || /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email) === false){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Invalid email</p>`,`<p style="font-size: 20px">You must enter a valid email</p>`)
        return
      }
      let password = this.$refs.password.value;
      let confirmPassword = this.$refs.confirmPassword.value;
      if(password === ''){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Invalid password</p>`,`<p style="font-size: 20px">You must enter a password</p>`)
        return
      }
      if(password !== confirmPassword){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Invalid passwords</p>`,`<p style="font-size: 20px">Passwords don't match</p>`)
        return
      }

      let reason = this.$refs.reason_input.value;
      let biography = this.$refs.biography_input.value;

      let user_type = this.$refs.user_type_input.value;

      if(user_type !== 'client'){
        if(reason === ''){
          this.show('foo-css', 'error',`<p style="font-size: 25px">Incomplete data</p>`,`<p style="font-size: 20px">You must enter all the info</p>`)
          return
        }
      }

      if(user_type === 'fishing instructor'){
        role = 'ROLE_fishingInstructor';
        if(biography === ''){
          this.show('foo-css', 'error',`<p style="font-size: 25px">Incomplete data</p>`,`<p style="font-size: 20px">You must enter all the info</p>`)
          return
        }
      }else if(user_type === 'cottage owner'){
        role = 'ROLE_cottageOwner';
      }else if(user_type === 'boat owner'){
        role = 'ROLE_boatOwner';
      }else{
        role = "ROLE_client";
      }
      this.info = {email, password, name, surname, city, country, street, postalCode, role, phoneNumber,reason,biography};

      axios
        .post("https://rental-app-6.herokuapp.com"+"/auth/register", this.info)
        .then(response => { this.registerSuccessful(role);})
        .catch((error) => { this.registerFailed(); })
    },

    registerSuccessful: function(role){
      if(role === "ROLE_client"){
        this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull registration</p>`,`<p style="font-size: 20px">Check you mail to activate your account!</p>`)
      }
      else{
        this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull registration</p>`,`<p style="font-size: 20px">Successfully created an account.</p>`)
      }

      setTimeout(() => { router.push('/login'); }, 1500);
    },

    registerFailed: function(){
      this.show('foo-css', 'error',`<p style="font-size: 25px">Invalid email</p>`,`<p style="font-size: 20px">There is already a user with this email.</p>`)
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
