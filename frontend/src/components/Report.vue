<template>
  <div class="main vh-100">
    <BoatOwnerNavbar v-if="role === 'ROLE_boatOwner'"></BoatOwnerNavbar>
    <CottageOwnerNavbar v-if="role === 'ROLE_cottageOwner'"></CottageOwnerNavbar>
    <FishingInstructorNavbar v-if="role === 'ROLE_fishingInstructor'" ></FishingInstructorNavbar>
    <div>
      <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
        <div class="container h-100" >
          <div class="row d-flex justify-content-center align-items-center h-100" >
            <div class="col-12 col-md-8 col-lg-7 col-xl-5" >
              <div class="card mt-5" style="border-radius: 15px; background: #ecd9c6; width: 1200px">
                <div class="card-body p-5">
                    <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Create report</h2>
                    <form>
                      <hr/>


                      <div class="form-check">
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" v-on:click="toggleSanction()" checked>
                        <label class="form-check-label" for="flexRadioDefault1">
                          Comment:
                        </label>
                      </div>

                      <textarea type="text" class="form-control form-control-lg" ref="comment_input" id="comment_input" />

                      <div class="form-check pt-2">
                        <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                          Sanction the client (1 Penalty)
                        </label>
                      </div>

                      <hr/>
                      <div class="form-check pb-3">
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" v-on:click="toggleClient()">
                        <label class="form-check-label" for="flexRadioDefault2">
                          Client did not show up (1 Penalty)
                        </label>
                      </div>


                      <div class="d-flex justify-content-center">
                        <button type="button" v-on:click="createReport()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Send report</div></button>
                      </div>
                    </form>

                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

</template>

<script>
import axios from "axios";
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "Report",
  components: {FishingInstructorNavbar, CottageOwnerNavbar, BoatOwnerNavbar},
  data: function (){
    return{
      clientId: '',
      role: sessionStorage.getItem("role")

    }
  }
  ,methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully!</p>`
      let text = `<p style="font-size: 20px">Successfully sent report!</p>`
      this.$notify({group, title, text, type})
    },
    createReport:function(){
      this.clientId = this.$route.params.clientId;
      console.log(this.clientId)

      this.info = {
        clientId: this.clientId,
        reportString: this.$refs.comment_input.value,
        didShowUp: document.getElementById('flexRadioDefault1').checked,
        sanction : document.getElementById('flexCheckDefault').checked
      }

      axios.post("https://rental-app-6.herokuapp.com"+"/api/clients/createReport/",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success')
          setTimeout(() => {this.$router.push({name:"ReservationHistory"}); }, 3000)
        }).catch(function error(error) {
        alert(error.response.data);
      });




    },
    toggleSanction:function (){
      document.getElementById('comment_input').disabled = false;
      document.getElementById('flexCheckDefault').disabled = false;
    },
    toggleClient:function (){
      document.getElementById('comment_input').disabled = true;
      document.getElementById('flexCheckDefault').disabled = true;
    }
  }
}
</script>

<style scoped>

.main{
  background-attachment: fixed;
  background-image: url('../assets/images/back2.jpg');
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
  width: 100%;
  object-fit: cover;
}

</style>
