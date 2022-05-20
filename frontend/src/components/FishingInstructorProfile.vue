<template>
  <div>
    <fishing-instructor-navbar></fishing-instructor-navbar>
  <section id = "client_profile" class="profile_main py-lg-3">

    <div  class="row py-5 px-auto">
      <div class="col-md-8 mx-auto">
        <div class="bg-white shadow rounded overflow-hidden">
          <div class="px-4 pt-0 pb-4 cover">
            <div class="media align-items-end profile-head">
              <div class="profile mr-3"><img :src="require('../assets/images/'+instructor.mainPhoto)" alt="..." width="250" class="rounded mb-2 img-thumbnail">
              </div>
            </div>
            <div class="pb-4 pt-4">
              <h4 class="mt-2 mb-0" style="color: white; float:left; padding-left: 5px" ><span>{{ this.instructor.name }}</span> <span>{{ this.instructor.surname }}</span></h4>
              <a href="#" class="btn flow delete-btn">Delete account</a>
            </div>
          </div>


          <div style="background-color: antiquewhite" class="container rounded mt-1" id = "fishing_instructor_profile">
            <div class="row">
              <div class="col-md-10 border-right">
                <div class="p-3 py-3">
                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">Personal data</h4>
                  </div>

                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">Name</label><input type="text" class="form-control" placeholder="first name" v-model="instructor.name" readonly> </div>
                    <div class="col-md-6 inputs"><label class="labels">Surname</label><input type="text" class="form-control" readonly v-model="instructor.surname" placeholder="Doe"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Email</label><input id="email" type="text" class="form-control" placeholder="email" readonly v-model="instructor.email"></div>
                    <div class="col-md-12 inputs"><label class="labels">Phone number</label><input type="text" class="form-control" placeholder="phone number" readonly v-model="instructor.phoneNumber"></div>
                    <div class="col-md-12 inputs"><label class="labels">Address</label><input type="text" class="form-control" placeholder="address" readonly v-model="address.street"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Country</label><input type="text" class="form-control" placeholder="country" readonly v-model="address.country"></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-6 inputs"><label class="labels">City</label><input type="text" class="form-control" readonly v-model="address.city" placeholder="city"></div>
                    <div class="col-md-6 inputs"><label class="labels">Postal code</label><input type="text" class="form-control" placeholder="postal code" readonly v-model="address.postalCode"/></div>
                  </div>
                  <div class="row mt-2">
                    <div class="col-md-12 inputs"><label class="labels">Biography</label><textarea type="text" class="form-control" placeholder="biography.." readonly v-model="instructor.biography"/></div>
                  </div>

                  <div class="mt-3 text-right"><button v-on:click="editClient" id="editButton" class="btn btn-primary edit-button" type="button">edit</button></div>
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
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "FishingInstructorProfile",
  components: {FishingInstructorNavbar},
  data: function(){
    return{
      instructor: '',
      address:'',
      inputs: null,
      editButton: null,
      readonly: true
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/getInstructor", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.instructor = response.data,this.address = this.instructor.address)).catch(function error(error) {
      alert(error.response.data);
    });

  },
  methods: {
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfull edit!</p>`
      let text = `<p style="font-size: 20px">Successfully edited data!</p>`
      this.$notify({group, title, text, type})
    },

    editClient: function() {
      this.inputs = document.querySelectorAll('input[type="text"]');
      document.querySelector('textarea[type="text"]').toggleAttribute('readonly');
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
        var c = {id:this.instructor.id,name :this.instructor.name,phoneNumber:this.instructor.phoneNumber, surname :this.instructor.surname, email :this.instructor.email, password :this.instructor.password,  address :this.address, biography: this.instructor.biography};
        axios
          .post(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/updateInstructor", c, {headers: {Authorization:
                'Bearer ' + sessionStorage.getItem("accessToken")}})
          .then(response => {
            this.show('foo-css', 'success')
          })

      }
    }

}
}
</script>

<style scoped>
#fishing_instructor_profile{
  height: 90vh;
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
