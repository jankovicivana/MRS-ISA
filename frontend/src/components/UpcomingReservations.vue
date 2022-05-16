<template>
  <section class="profile_main vh-100" >

    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">
                <div class="card-body p-5">

                  <h1 class="title">Upcoming reservations</h1>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr style="background: #ecd9c6">
                        <th>Cottage</th>
                        <th>Client</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>No. of people</th>
                        <th>Price</th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr style="background: #ecd9c6;" v-for="reservation in reservations">
                        <td>{{reservation.cottage.name}}</td>
                        <td>{{reservation.client.surname + " "+ reservation.client.name}}</td>
                        <td>{{reservation.startDateTime[2]+"."+reservation.startDateTime[1]+"."+reservation.startDateTime[0]+"."}}</td>
                        <td>{{reservation.endDateTime[2]+"."+reservation.endDateTime[1]+"."+reservation.endDateTime[0]+"."}}</td>
                        <td class="d-flex justify-content-center">{{reservation.personNum}}</td>
                        <td>{{reservation.price}}</td>
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

export default {
  name: "UpcomingReservations",
  data(){

    return{
      reservations: '',
    }
  },
  mounted:function (){

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findUpcomingByCottageOwner/1")
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
