<template>
  <section class="profile_main vh-100" >
  <fishing-instructor-navbar></fishing-instructor-navbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">
                <div class="card-body p-5">

                  <h1 class="title">Reservation history</h1>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr style="background: #ecd9c6">
                        <th>Adventure</th>
                        <th>Client</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>No. of people</th>
                        <th>Price</th>
                        <th>Report</th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr style="background: #ecd9c6;" v-for="reservation in reservations">
                        <td>{{reservation.adventure.name}}</td>
                        <td>{{reservation.client.surname + " "+ reservation.client.name}}</td>
                        <td>{{reservation.startDateTime[2]+"."+reservation.startDateTime[1]+"."+reservation.startDateTime[0]+"."}}</td>
                        <td>{{reservation.endDateTime[2]+"."+reservation.endDateTime[1]+"."+reservation.endDateTime[0]+"."}}</td>
                        <td class="d-flex justify-content-center">{{reservation.personNum}}</td>
                        <td>{{reservation.price}}</td>

                        <td><button v-on:click="createReport(reservation)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Create report</button></td>
                      </tr>
                      </tbody>

                    </table>
                  </div>
                </div>
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
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "ReservationHistory",
  components: {FishingInstructorNavbar},
  data(){

    return{
      reservations: '',
    }
  },
  mounted:function (){

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findHistoryByUser/3", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.reservations = response.data
      ))


  },
  methods:{
    createReport:function (reservation){

    }

  }
}
</script>

<style scoped>

</style>
