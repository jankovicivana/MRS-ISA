<template>
  <div>
    <client_navbar v-if="role === 'ROLE_client'"></client_navbar>
    <cottage-owner-navbar v-if="role === 'ROLE_cottageOwner'"></cottage-owner-navbar>
    <section id = "client_profile" class="profile_main py-lg-3">
    <div  class="row py-5 px-auto" style="align-content: center">
      <div class="col-md-8 mx-auto">
        <div class="bg-white shadow rounded overflow-hidden">
          <div class="px-4 pt-0 pb-4 cover">
            <div class="media align-items-end profile-head">
              <div class="profile mr-3"><img src="../assets/images/default_profile.jpg" alt="Image" width="150" class="rounded mb-2 img-thumbnail">
              </div>
            </div>
            <div class="pb-4 pt-4">
              <h4 class="mt-2 mb-0" style="color: white; float:left; padding-left: 5px" ><span>{{client.name}}</span> <span>{{client.surname}}</span></h4>
              <a href="#/" class="btn delete-btn">Delete account</a>
            </div>
          </div>


          <div style="background-color: antiquewhite" class="container rounded mt-1" id = "client_profile_container">
            <div class="row">
              <div class="col-md-6 border-right">
                <div class="p-3 py-3">
                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6 class="text-right">Personal data</h6>
                  </div>

                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">Name</label><input type="text" class="form-control" placeholder="first name" v-model="client.name" readonly> </div>
                    <div class="col-md-6 inputs"><label class="labels">Surname</label><input type="text" class="form-control" readonly v-model="client.surname" placeholder="last name"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Email</label><input id="email" type="text" class="form-control" placeholder="email" readonly v-model="client.email"></div>
                    <div class="col-md-12 inputs"><label class="labels">Phone number</label><input type="text" class="form-control" placeholder="phone number" readonly v-model="client.phoneNumber"></div>
                    <div class="col-md-12 inputs"><label class="labels">Address</label><input type="text" class="form-control" placeholder="address" readonly v-model="this.client.address.street"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Country</label><input type="text" class="form-control" placeholder="country" readonly v-model="this.client.address.country"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">City</label><input type="text" class="form-control" readonly v-model="this.client.address.city" placeholder="city"></div>
                    <div class="col-md-6 inputs"><label class="labels">Postal code</label><input type="text" class="form-control" placeholder="postal code" readonly v-model="this.client.address.postalCode"></div>
                  </div>

                  <div class="mt-3 text-right"><button v-on:click="editClient" id="editButton" class="btn btn-primary edit-button" type="button">edit</button></div>
                </div>
              </div>

              <div class="col-md-6 border-right">

                <div class="p-3 py-3">
                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6>Loyalty program</h6>
                  </div>

                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6>&#127941; Status: Regular </h6>
                    <h6>&#11088; Points: <span>{{client.points}}</span> </h6>
                    <h6>&#10060; Penalties: <span>{{client.penalties}}</span></h6>
                  </div>

                  <div class="d-flex justify-content-between align-items-center mb-3 mt-5">
                    <p><i>What is loyalty program? You get points with each reservation you make. Collect points to gain
                      a certain user status which brings you
                      special benefits.
                    </i></p>
                  </div>

                  <div style="background: #ecd9c6; border:2px solid #ecd9c6" class="d-flex justify-content-between align-items-center mb-3 mt-3">
                    <h5> <span style="color: slategrey; padding-left: 5px;  margin-left: 20px">&#127941;  SILVER</span> </h5>
                    <span style="margin-right: 10px; padding-right: 25px;">
                    <ul>
                      <li>Collect 20 points to unlock</li>
                      <li>10% off for all reservations</li>
                    </ul>
                      </span>
                  </div>

                  <div style="background: #ecd9c6; border:2px solid #ecd9c6" class="d-flex justify-content-between align-items-center mt-3">
                    <h5> <span style="color: gold; padding-left: 5px; margin-left: 20px">&#127941;  GOLD</span> </h5>
                    <span style="margin-right: 10px; padding-right: 25px;">
                    <ul>
                      <li>Collect 50 points to unlock</li>
                      <li>20% off for all reservations</li>
                      <li>No penalties</li>
                    </ul>
                    </span>
                  </div>

                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </section>
  </div>


</template>

<script>
import axios from "axios";
import ClientNavbar from "./header/ClientNavbar";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";

export default {
  name: "ClientProfile",
  components: {'client_navbar': ClientNavbar,CottageOwnerNavbar},
  data: function(){
    return{
      client: '',
      address: '',
      inputs: null,
      editButton: null,
      readonly: true,
      role:''
    }
  },
  mounted: function (){
    this.role = sessionStorage.getItem("role");
    if (this.role === "ROLE_cottageOwner" || this.role === "ROLE_fishingInstructor" ) {
      var clientId = this.$route.params.id;
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/clients/"+clientId, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.client = response.data))
    }
    if (this.role === "ROLE_client") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/clients/getClient", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (this.client = response.data))
    }

  },

  methods: {
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfull edit!</p>`
      let text = `<p style="font-size: 20px">Successfully edited data!</p>`
      this.$notify({group, title, text, type})
    },

    editClient: function() {
      this.inputs = document.querySelectorAll('input[type="text"]');
      for (var i=0; i<this.inputs.length; i++) {
        if(this.inputs[i].getAttribute("id") !== "email"){
          this.inputs[i].toggleAttribute('readonly');
        }
      }
      this.editButton = document.getElementById('editButton');
      if (this.editButton.innerHTML==="edit" ) {
        this.editButton.innerHTML="save" ;
      } else {
        this.editButton.innerHTML="edit" ;
        let c = {id: this.client.id, name: this.client.name, surname: this.client.surname, email: this.client.email, password: this.client.password, address: this.client.address};
        axios
          .post(process.env.VUE_APP_SERVER_PORT+"/api/clients/updateClient", c)
          .then(response => {
            this.show('foo-css', 'success')
          })
      }
    }
  }
}
</script>

<style scoped>

</style>
