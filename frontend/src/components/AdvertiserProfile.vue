<template>
  <div>
    <CottageOwnerNavbar v-if="role === 'ROLE_cottageOwner'"></CottageOwnerNavbar>
    <BoatOwnerNavbar v-if="role === 'ROLE_boatOwner'"></BoatOwnerNavbar>
    <fishing-instructor-navbar v-if="role === 'ROLE_fishingInstructor'"></fishing-instructor-navbar>
    <AdminNavbar v-if="role === 'ROLE_admin'" :isAdmin="true"></AdminNavbar>

  <section id = "client_profile" class="profile_main py-lg-3">

    <div  class="row py-5 px-auto">
      <div class="col-md-8 mx-auto">
        <div class="bg-white shadow rounded">
          <div class="px-4 pt-0 pb-4 cover">
            <div class="media align-items-end profile-head">
              <div class="profile mr-3"><img :src="mainPhoto" alt="..." width="250" class="rounded mb-2 img-thumbnail">
              </div>
            </div>
            <div class="pb-4 pt-4">
              <h4 class="mt-2 mb-0" style="color: white; float:left; padding-left: 5px" ><span>{{this.advertiser.name }}</span> <span>{{ this.advertiser.surname }}</span></h4>
              <a v-if="isAdmin || role!=='ROLE_admin'" href="#" class="btn flow delete-btn">Delete profile</a>
            </div>
          </div>


          <div style="background-color: antiquewhite" class="container rounded mt-1" id = "cottage_owner_profile">
            <div class="row">
              <div class="col-md-10 border-right">
                <div class="p-3 py-3">
                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">Personal data</h4>
                  </div>

                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">Name</label><input type="text" class="form-control" placeholder="first name" v-model="advertiser.name" readonly> </div>
                    <div class="col-md-6 inputs"><label class="labels">Surname</label><input type="text" class="form-control" readonly v-model="advertiser.surname" placeholder="Doe"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Email</label><input id="email" type="text" class="form-control" placeholder="email" readonly v-model="advertiser.email"></div>
                    <div class="col-md-12 inputs"><label class="labels">Phone number</label><input type="text" class="form-control" placeholder="phone number" readonly v-model="advertiser.phoneNumber"></div>
                    <div class="col-md-12 inputs"><label class="labels">Address</label><input type="text" class="form-control" placeholder="address" readonly v-model="address.street"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Country</label><input type="text" class="form-control" placeholder="country" readonly v-model="address.country"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">City</label><input type="text" class="form-control" readonly v-model="address.city" placeholder="city"></div>
                    <div class="col-md-6 inputs"><label class="labels">Postal code</label><input type="text" class="form-control" placeholder="postal code" readonly v-model="address.postalCode"/></div>
                  </div>

                  <div v-if="role === 'ROLE_fishingInstructor'" class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Biography</label><textarea type="text" class="form-control" placeholder="biography.." readonly v-model="advertiser.biography"/></div>
                  </div>
                  <div class="row">
                    <div v-if="isAdmin || role!=='ROLE_admin'" class="col-2 mt-3 text-right"><button v-on:click="editAdvertiser" id="editButton" class="btn btn-primary edit-button" type="button">edit</button></div>
                    <div v-if="isAdmin || role!=='ROLE_admin'" class="col-4 mt-3 text-right" ><button v-on:click="showChangePass" id="editButton" class="btn btn-primary edit-button" type="button" >Change password</button></div>
                  </div>
                  <div class="row mt-4 mb-3" id="change_pass" hidden>
                    <div class="col-md-5 inputs"><label class="labels">Current password</label><input id="current" type="password" ref="current" class="form-control" placeholder="current password" ></div>
                    <div class="col-7"></div>
                    <div class="col-md-5 inputs"><label class="labels">New password</label><input id="new_pass" type="text" ref="new_pass" class="form-control" placeholder="new password" ></div>
                    <div class="col-7"></div>
                    <div class="col-md-5 inputs"><label class="labels">Re-enter new password</label><input  type="text" ref="new_pass_2" class="form-control" placeholder="re-enter new password" ></div>
                    <div class="col-7"></div>
                    <div v-if="isAdmin || role!=='ROLE_admin'" class="col-2 mt-3 text-right"><button v-on:click="savePass" id="editButton" class="btn btn-primary edit-button" type="button">save</button></div>
                  </div>

                </div>
              </div>

              <div class="col-md-6 border-right">

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
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";
import AdminNavbar from "./header/AdminNavbar";
import router from "../router";

export default {
  name: "AdvertiserProfile",
  components: {AdminNavbar, FishingInstructorNavbar, BoatOwnerNavbar, CottageOwnerNavbar},
  data: function(){
    return{
      advertiser: '',
      address:'',
      inputs: null,
      editButton: null,
      readonly: true,
      role:'',
      isAdmin: this.$route.params.isAdmin,
      mainPhoto:''
    }
  },
  mounted: function (){
    this.role = sessionStorage.getItem("role");
    if (this.role === "ROLE_cottageOwner") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/cottageOwner/getCottageOwner", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (this.advertiser = response.data, this.address = this.advertiser.address,this.loadOnlyOneImage(response.data.mainPhoto))).catch(function error(error) {
        alert(error.response.data);
      });
    }else if (this.role === "ROLE_boatOwner") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/boatOwner/getBoatOwner", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (this.advertiser = response.data, this.address = this.advertiser.address,this.loadOnlyOneImage(response.data.mainPhoto))).catch(function error(error) {
        alert(error.response.data);
      });
    }else if (this.role === "ROLE_fishingInstructor") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/getInstructor", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.advertiser = response.data,this.address = this.advertiser.address,this.loadOnlyOneImage(response.data.mainPhoto))).catch(function error(error) {
        alert(error.response.data);
      });
    }
    else if(this.role === "ROLE_admin"){
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/administrator/getAdmin", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.advertiser = response.data,this.address = this.advertiser.address,this.loadOnlyOneImage(response.data.mainPhoto))).catch(function error(error) {
        alert(error.response.data);
      });
    }
    console.log(this.isAdmin)
  },
  methods: {
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },
    loadOnlyOneImage(name) {
      axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+name,{responseType:"blob"})
        .then(response => {
          this.mainPhoto=URL.createObjectURL(response.data);
        })
        .catch((error) =>{
          console.log(error);
        });
    },
    editAdvertiser: function() {
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
        var c = {id:this.advertiser.id,name :this.advertiser.name,phoneNumber:this.advertiser.phoneNumber, surname :this.advertiser.surname, email :this.advertiser.email, password :this.advertiser.password,  address :this.address, biography: this.advertiser.biography};
        if (this.role === "ROLE_cottageOwner") {
          axios
            .post(process.env.VUE_APP_SERVER_PORT + "/api/cottageOwner/updateCottageOwner", c, {headers: {Authorization:
                  'Bearer ' + sessionStorage.getItem("accessToken")}})
            .then(response => {
              this.show('foo-css', 'success',`<p style="font-size: 25px">Successful edit</p>`,`<p style="font-size: 20px">Successfully edited data!</p>`)
            })
        }else if (this.role === "ROLE_boatOwner") {
          axios
            .post(process.env.VUE_APP_SERVER_PORT + "/api/boatOwner/updateBoatOwner", c, {headers: {Authorization:
                  'Bearer ' + sessionStorage.getItem("accessToken")}})
            .then(response => {
              this.show('foo-css', 'success',`<p style="font-size: 25px">Successful edit</p>`,`<p style="font-size: 20px">Successfully edited data!</p>`)
            })
        }else if (this.role === "ROLE_fishingInstructor") {
          axios
            .post(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/updateInstructor", c, {headers: {Authorization:
                  'Bearer ' + sessionStorage.getItem("accessToken")}})
            .then(response => {
              this.show('foo-css', 'success',`<p style="font-size: 25px">Successful edit</p>`,`<p style="font-size: 20px">Successfully edited data!</p>`)
            });
        }else if (this.role === "ROLE_admin") {
          axios
            .post(process.env.VUE_APP_SERVER_PORT+"/api/administrator/updateAdmin", c, {headers: {Authorization:
                  'Bearer ' + sessionStorage.getItem("accessToken")}})
            .then(response => {
              this.show('foo-css', 'success',`<p style="font-size: 25px">Successful edit</p>`,`<p style="font-size: 20px">Successfully edited data!</p>`)
            });
        }
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

    }

  }
}
</script>

<style scoped>
#cottage_owner_profile{
  padding-left: 15%;
  display: inline-block;
  justify-content: center;
  align-items: center;
  background-color: burlywood;
}


.inputs textarea {
  padding: 0px 10px;
  font-size: 17px;
  box-shadow: none;
  outline: none;
  background-color: white;
}



</style>

