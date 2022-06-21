<template>
  <section class="vh-80" style="background-image: url('https://images.unsplash.com/photo-1470770841072-f978cf4d019e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80'); background-size: 100% 100%;">
    <admin_navbar :isAdmin="true"></admin_navbar>
    <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
      <div class="container h-100" >
        <div class="row d-flex justify-content-center align-items-center h-100" >
          <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
            <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
              <div class="card-body p-5">
              <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Register administrator</h2>
                <form>
                  <h4>Contact Details</h4>

                  <div class="form-outline mb-4">
                    <label class="label">Email:</label>
                    <div>
                      <input class="form-control form-control-lg" ref="email_input" type="text"   placeholder="Email input" />
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="label">Contact Phone:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text" ref="phone_input"   placeholder="Phone  input" />
                    </div>
                  </div>
                  <hr />
                  <h4>Personal Details</h4>
                  <div class="form-outline mb-4">
                    <label class="label">First name:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text" ref="first_name_input"   placeholder="First name input" />
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="label">Last name:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text"  ref="last_name_input" placeholder="Last name input" />
                    </div>
                  </div>

                  <hr />
                  <h4>Address Details</h4>
                  <div class="form-outline mb-4">
                    <label class="label">Street:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text" ref="street_input"  placeholder="Street input" />
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="label">City:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text" ref="city_input"  placeholder="City input" />
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="label">State:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text" ref="state_input"   placeholder="State input" />
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="label">Postal code:</label>
                    <div>
                      <input class="form-control form-control-lg" type="text"  ref="postal_code_input" placeholder="Postal code input" />
                    </div>
                  </div>

                  <hr />
                  <h4>Account Info</h4>
                  <div class="form-outline mb-4">
                    <label class="label">Enter password:</label>
                    <div>
                      <input class="form-control form-control-lg" type="password"  ref="password" placeholder="Enter your password" />
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="label">Confirm password:</label>
                    <div>
                      <input class="form-control form-control-lg" type="password"  ref="confirmed_password" placeholder="Confirm password" />
                    </div>
                  </div>
                  <div class="d-flex justify-content-center">
                    <button type="button" v-on:click="addAdministrator()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
                  </div>
                </form>
                <br/>

              </div>

            </div>
          </div>
        </div>
      </div>
    </div>

  </section>
</template>

<script>
import axios from "axios";
import AdminNavbar from "./header/AdminNavbar";

export default {
  name: "AddAdministrator",
  data() {

    return {
      result: '',
    }
  },
  components: {'admin_navbar': AdminNavbar},

  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfull adding!</p>`
      let text = `<p style="font-size: 20px">Successfully added administrator!</p>`
      this.$notify({group, title, text, type})
    },show1: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },


    addAdministrator:function (){
      let password = this.$refs.password.value
      let confirmed_password = this.$refs.confirmed_password.value

      if(password !== confirmed_password){
        this.show1('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Passwords don't match.Try again!</p>`)
        return;
      }

      let email = this.$refs.email_input.value

      let first_name = this.$refs.first_name_input.value
      let last_name = this.$refs.last_name_input.value
      let phone = this.$refs.phone_input.value
      let state = this.$refs.state_input.value
      let city = this.$refs.city_input.value
      let street = this.$refs.street_input.value
      let postal_code = this.$refs.postal_code_input.value

      this.info = {
        name: first_name,
        surname: last_name,
        country:state,
        city:city,
        street: street,
        postalCode: postal_code,
        email: email,
        phoneNumber: phone,
        password: password
      };
      for (let i of [first_name,last_name,state,city,email,city,street,postal_code,phone,password]) {
        if (i === '') {
          this.show1('foo-css', 'error', `<p style="font-size: 25px">Warning!</p>`, `<p style="font-size: 20px">You must enter all info!</p>`)
          return;
        }
      }


      axios.post("https://rental-app-6.herokuapp.com"+"/api/administrator/addAdministrator",this.info ,{headers: {Authorization:
        'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success')
          setTimeout(() => {location.reload(); }, 3000)
        }).catch((error) =>  {
        this.show1('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Email is already used!</p>`)
        });

    },


  }
}
</script>

<style scoped>
.title {
  font-size: 3rem;
}

.input {
  min-width: 187px;
}
.body-wrapper {
  min-height: calc(100vh - 168px);
  alignment: center;
}

</style>
