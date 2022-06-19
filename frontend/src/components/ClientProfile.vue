<template>
  <div>
    <client_navbar v-if="role === 'ROLE_client'"></client_navbar>
    <cottage-owner-navbar v-if="role === 'ROLE_cottageOwner'"></cottage-owner-navbar>
    <boat-owner-navbar v-if="role === 'ROLE_boatOwner'" ></boat-owner-navbar>
    <FishingInstructorNavbar v-if="role === 'ROLE_fishingInstructor'"></FishingInstructorNavbar>
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
              <button v-if="role==='ROLE_client'"  @click="showModal = true" class="btn flow delete-btn">Delete profile</button>
            </div>
          </div>

          <div id="app">
            <div v-if="showModal">
              <transition name="modal">
                <div class="modal-mask">
                  <div class="modal-wrapper">
                    <div class="modal-dialog" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">Account delete request</h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true" @click="showModal = false">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <p>Enter reason to delete account.</p>
                          <textarea type="text" class="form-control form-control-lg" ref="request_input" id="request_input"></textarea>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" @click="showModal = false">CLOSE</button>
                          <button type="button" class="btn btn-primary edit-button" v-on:click="sendRequest()">Send</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </transition>
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
                    <div class="col-md-12 inputs"><label class="labels">Address</label><input type="text" class="form-control" placeholder="address" readonly v-model="address.street"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Country</label><input type="text" class="form-control" placeholder="country" readonly v-model="address.country"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">City</label><input type="text" class="form-control" readonly v-model="address.city" placeholder="city"></div>
                    <div class="col-md-6 inputs"><label class="labels">Postal code</label><input type="text" class="form-control" placeholder="postal code" readonly v-model="address.postalCode"></div>
                  </div>
                  <div class="row">
                    <div v-if="role === 'ROLE_client'" class="col-2 mt-3 text-right"><button v-on:click="editClient" id="editButton" class="btn btn-primary edit-button" type="button">edit</button></div>
                    <div v-if="role === 'ROLE_client'" class="col-7 mt-3 text-right" ><button v-on:click="showChangePass" id="editButton" class="btn btn-primary edit-button" type="button" >Change password</button></div>
                  </div>

                  <div class="row mt-4 mb-3" id="change_pass" hidden>
                    <div class="col-md-6 inputs"><label class="labels">Current password</label><input id="current" type="password" ref="current" class="form-control" placeholder="current password" ></div>
                    <div class="col-6"></div>
                    <div class="col-md-6 inputs"><label class="labels">New password</label><input id="new_pass" type="password" ref="new_pass" class="form-control" placeholder="new password" ></div>
                    <div class="col-6"></div>
                    <div class="col-md-6 inputs"><label class="labels">Re-enter new password</label><input  type="password" ref="new_pass_2" class="form-control" placeholder="re-enter new password" ></div>
                    <div class="col-6"></div>
                    <div  v-if="role === 'ROLE_client'" class="col-2 mt-3 text-right"><button v-on:click="savePass" id="editButton" class="btn btn-primary edit-button" type="button">save</button></div>
                  </div>
                </div>


              </div>

              <div class="col-md-6 border-right">

                <div class="p-3 py-3">
                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6>Loyalty program</h6>
                  </div>

                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h6>&#127941; Status: {{ status }} </h6>
                    <h6>&#11088; Points: <span>{{client.points}}</span> </h6>
                    <h6>&#10060; Penalties: <span>{{client.penalties}}</span></h6>
                  </div>

                  <div class="d-flex justify-content-between align-items-center mb-3 mt-5">
                    <p><i>What is loyalty program? You get {{systemInfo.clientPoints}} points with each reservation you make. Collect points to gain
                      a certain user status which brings you
                      special benefits.
                    </i></p>
                  </div>

                  <div style="background: #ecd9c6; border:2px solid #ecd9c6" class="d-flex justify-content-between align-items-center mb-2 mt-3">
                    <h5> <span style="color: slategrey; padding-left: 5px;  margin-left: 20px">&#127941;  SILVER</span> </h5>
                    <span style="margin-right: 10px; padding-right: 25px;">
                    <ul class="pt-3">
                      <li>Collect {{ regular.pointsLimit }} points to unlock</li>
                      <li>{{ silver.clientDiscount }}% off of all reservations</li>
                    </ul>
                      </span>
                  </div>

                  <div style="background: #ecd9c6; border:2px solid #ecd9c6" class="d-flex justify-content-between align-items-center mt-3">
                    <h5> <span style="color: goldenrod; padding-left: 5px; margin-left: 20px">&#127941;  GOLD</span> </h5>
                    <span style="margin-right: 10px; padding-right: 25px;">
                    <ul class="pt-3">
                      <li>Collect {{ gold.pointsLimit }} points to unlock</li>
                      <li>{{gold.clientDiscount}}% off of all reservations</li>
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
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "ClientProfile",
  components: {FishingInstructorNavbar, BoatOwnerNavbar, 'client_navbar': ClientNavbar,CottageOwnerNavbar},
  data: function(){
    return{
      client: '',
      address: '',
      inputs: null,
      editButton: null,
      readonly: true,
      role:'',
      systemInfo: '',
      regular: '',
      silver: '',
      gold: '',
      status: '',
      showModal:false
    }
  },
  mounted: function (){
    this.role = sessionStorage.getItem("role");
    if (this.role === "ROLE_cottageOwner" || this.role === "ROLE_fishingInstructor" || this.role === "ROLE_boatOwner" ) {
      var clientId = this.$route.params.id;
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/clients/"+clientId, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.client = response.data, this.address = this.client.address))
    }
    if (this.role === "ROLE_client") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/clients/getClient", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => {this.client = response.data
                           this.address = this.client.address
          axios
            .get(process.env.VUE_APP_SERVER_PORT+"/api/system/getRankingInfo/1", {headers: {Authorization:
                  'Bearer ' + sessionStorage.getItem("accessToken")}})
            .then(response => {
              this.regular = response.data

              axios
                .get(process.env.VUE_APP_SERVER_PORT + "/api/system/getRankingInfo/2", {
                  headers: {
                    Authorization:
                      'Bearer ' + sessionStorage.getItem("accessToken")
                  }
                })
                .then(response => {
                  this.silver = response.data

                  axios
                    .get(process.env.VUE_APP_SERVER_PORT+"/api/system/getRankingInfo/3", {headers: {Authorization:
                          'Bearer ' + sessionStorage.getItem("accessToken")}})
                    .then(response => {
                      this.gold = response.data
                      if (this.client.points < this.regular.pointsLimit) {
                        this.status = "Regular"
                      } else if (this.client.points < this.silver.pointsLimit) {
                        this.status = "Silver"
                      } else {
                        this.status = "Gold"
                      }

                    });

                });

            });

        }).catch(function error(error) {
        alert(error.response.data);
      });

      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/system/getSystemInfo", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (this.systemInfo = response.data)).catch(function error(error) {
        alert(error.response.data);
      });

    }

  },

  methods: {
    show: function(group, type='',title,text){
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
        var c = {id: this.client.id, name: this.client.name, surname: this.client.surname,
          email: this.client.email, password: this.client.password, address: this.address,
          phoneNumber: this.client.phoneNumber, penalties: this.client.penalties, points: this.client.points};
        axios
          .post(process.env.VUE_APP_SERVER_PORT+"/api/clients/updateClient", c, {
            headers: {
              Authorization:
                'Bearer ' + sessionStorage.getItem("accessToken")
            }
          })
          .then(response => {
            this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull edit</p>`,`<p style="font-size: 20px">Successfully edited data!</p>`)
          })
      }
    },

    showChangePass:function (){
      if(document.getElementById('change_pass').hidden===true){
        document.getElementById('change_pass').hidden=false;
      }else{
        document.getElementById('change_pass').hidden=true;
      }

    },
    savePass:function (){
      let current_pass = this.$refs.current.value;
      let new_pass = this.$refs.new_pass.value;
      let new_pass2 = this.$refs.new_pass_2.value;
      if(new_pass !== new_pass2){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Unsuccessful change</p>`,`<p style="font-size: 20px">Entered passwords are not same!</p>`)
      }else if (new_pass.length === 0 || new_pass2.length === 0 || current_pass.length === 0)
      {
        this.show('foo-css', 'error',`<p style="font-size: 25px">Unsuccessful change</p>`,`<p style="font-size: 20px">Password can not be empty!</p>`)
      }else{
        let c = {currentPassword:current_pass,newPassword:new_pass};
        axios
          .post(process.env.VUE_APP_SERVER_PORT+"/api/user/changePassword", c, {headers: {Authorization:
                'Bearer ' + sessionStorage.getItem("accessToken")}})
          .then(response => {
            this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull change</p>`,`<p style="font-size: 20px">Successfully changed password!</p>`)
            sessionStorage.clear();
            setTimeout(() => {this.$router.push({name:"LoginPage"}); }, 2000)
          }).catch((error) =>{
          console.log(error);
          this.show('foo-css', 'error',`<p style="font-size: 25px">Unsuccessful change</p>`,`<p style="font-size: 20px">Current password does not match!</p>`)
        });
      }

    },
    sendRequest:function (){
      this.showModal = false;
      console.log(this.$refs.request_input.value)
      if(this.$refs.request_input.value.length === 0){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Unsuccessful</p>`,`<p style="font-size: 20px">You must enter reason!</p>`)
        return;
      }
      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/user/deleteAccount", {requestReason:this.$refs.request_input.value}, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successful</p>`,`<p style="font-size: 20px">Successfully sent request!</p>`)
          setTimeout(() => { }, 2000)
        }).catch((error) =>{
        console.log(error);
      });
    }

  }
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
</style>
