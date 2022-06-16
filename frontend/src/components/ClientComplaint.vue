<template>
  <div class="main vh-100">
    <client-navbar></client-navbar>
    <div>
      <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
        <div class="container h-100" >
          <div class="row d-flex justify-content-center align-items-center h-100" >
            <div class="col-12 col-md-8 col-lg-7 col-xl-5" >
              <div class="card mt-5" style="border-radius: 15px; background: #ecd9c6; width: 1200px">
                <div class="card-body p-5">
                  <h2 class="text-uppercase text-center mb-3" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Write a complaint</h2>
                  <hr/>
                  <form class="mt-3">
                    <div v-if="reservation.entity.type !== 'Adventure' ">
                      <div class="columns pb-0">
                        <div class="column pt-3 pb-0">
                          <label>{{ reservation.entity.type }}: <b>{{reservation.entity.name}} </b></label>
                        </div>
                      </div>

                      <textarea type="text" class="form-control form-control-lg" ref="entityComplaint" id="entityComplaint" />
                      <hr/>
                    </div>


                    <div class="pt-2 pb-2">
                      <div class="columns mb-0">
                        <div class="column pt-3">
                          <label v-if="reservation.entity.type !== 'Adventure'">
                            {{ reservation.entity.type }} owner: <b>{{ownerName}} </b>
                          </label>
                          <label v-else>
                            Fishing instructor <br/> <b>{{ownerName}} </b>
                          </label>
                        </div>
                      </div>
                    </div>

                    <textarea type="text" class="form-control form-control-lg" ref="ownerComplaint" id="ownerComplaint" />

                    <div class="d-flex justify-content-center pt-4">
                      <button v-if="this.reservation.isComplained" title="Already sent a complaint" style="background-color: grey;" class="button">Send</button>
                      <button v-else v-on:click="sendComplaint()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><span style="color:white">Send</span></button>
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
import ClientNavbar from "./header/ClientNavbar";

export default {
  name: "ClientComplaint",
  components: {ClientNavbar},
  data: function (){
    return{
      reservationId: this.$route.params.id,
      reservation: '',
      ownerName: ''
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/"+this.reservationId, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {
        this.reservation = response.data;
        if(this.reservation.entity.type === "Cottage"){
          this.ownerName = this.reservation.cottage.cottageOwnerName;
        } else if (this.reservation.entity.type === "Boat"){
          this.ownerName = this.reservation.boat.boatOwnerName;
        } else{
          this.ownerName = this.reservation.adventure.fishingInstructor.name + " " + this.reservation.adventure.fishingInstructor.surname;
        }
      })
  },
  methods: {
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Complaint sent</p>`
      let text = `<p style="font-size: 15px">Complaint successfully sent!</p>`
      this.$notify({group, title, text, type})
    },

    sendComplaint: function (){
      var data = ''
      if(this.reservation.entity.type === "Adventure"){
        data = {clientId: this.reservation.client.id, entityId: this.reservation.entity.id,
          entityComplaint: "", ownerComplaint: this.$refs.ownerComplaint.value,
          reservationId: this.reservationId
        }
      } else{
        data = {clientId: this.reservation.client.id, entityId: this.reservation.entity.id,
          entityComplaint: this.$refs.entityComplaint.value, ownerComplaint: this.$refs.ownerComplaint.value,
          reservationId: this.reservationId
        }
      }

      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/complaint/addComplaint", data,{
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }})
        .then(response => {
          this.show('foo-css', 'success');
          this.$router.push({name: "ClientReservationHistory"}); }).catch((error) => {alert("error")});

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
