<template>
  <section class="profile_main vh-100">
    <ClientNavbar></ClientNavbar>
    <div class="content is-medium">
      <div class="mask flex align-items-center pt-5 h-100">
        <div class="container h-100">
          <div class="row flex justify-content-center h-100">
            <div class="col-11">
              <div class="card">
                <div class="card-body p-5">
                  <h1 class="title">Upcoming reservations</h1>
                  <hr/>
                  <div>
                    <table>
                      <thead>
                      <tr>
                        <th>Type</th>
                        <th>Name</th>
                        <th>Start</th>
                        <th>End</th>
                        <th>Location</th>
                        <th>Price</th>
                        <th>Cancel</th>
                      </tr>
                      </thead>

                      <tbody>
                      <tr v-for="r in reservations">
                        <td>{{r.entity.type}}</td>
                        <td>{{r.entity.name}}</td>
                        <td>{{r.startDateTime[2]+"."+r.startDateTime[1]+"."+r.startDateTime[0]+"."}}</td>
                        <td>{{r.endDateTime[2]+"."+r.endDateTime[1]+"."+r.endDateTime[0]+"."}}</td>
                        <td>{{r.entity.address.city+", "+r.entity.address.country}}</td>
                        <td>{{r.price}}</td>

                        <td>
                          <button v-if="canCancel(r)" v-on:click="cancel(r)">Cancel</button>
                          <button v-else class="disabled" disabled>Cancel</button>
                        </td>
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
import ClientNavbar from "./header/ClientNavbar";
import axios from "axios";
export default {
  name: "ClientUpcomingReservations",
  components: {ClientNavbar},
  data: function (){
    return{
      reservations: ''
    }
  },

  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findUpcomingByClient", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.reservations = response.data
      ));
  },

  methods: {
    canCancel: function (res){
      var today=  new Date();
      var start =  new Date(res.startDateTime[0], res.startDateTime[1]-1, res.startDateTime[2],
                            res.startDateTime[3], res.startDateTime[4]);
      var time_diff = start - today;
      var days_diff = time_diff / (24*60*60*1000);
      return days_diff > 3;

    },

    cancel: function (){

    }
  }
}
</script>

<style scoped>

.content{
  height: 80%;
}

.card{
  border-radius: 15px;
  background: #ecd9c6;
  height: 100%;
}

tr{
  background: #ecd9c6;
}

button{
  background: #2e6b6b;
  border-radius: 8px;
  color: #FFFFFF;
  border-color: #FFFFFF
}

select{
  border: none;
  background-color: #ecd9c6;
}

button.disabled{
  background: grey;
}

</style>
