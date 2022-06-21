<template>
  <section class="profile_main min-vh-100 is-fullheight">
    <ClientNavbar></ClientNavbar>
    <div class="content is-medium">
      <div class="mask flex align-items-center pt-5 h-100">
        <div class="container h-100 pb-3">
          <div class="row flex justify-content-center h-100">
            <div class="col-11">
              <div class="card">
                <div class="card-body p-5">
                  <h1 class="title">Upcoming reservations</h1>
                  <hr/>
                  <div>
                    <table>
                      <thead>
                      <tr style="background: #e3c7aa">
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
                      <tr style="background:  #ede4da;" v-for="r in reservations">
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
      .get("https://rental-app-6.herokuapp.com"+"/api/reservation/findUpcomingByClient", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.reservations = response.data
      ));
  },

  methods: {
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Canceled!</p>`
      let text = `<p style="font-size: 20px">Successfully canceled reservation!</p>`
      this.$notify({group, title, text, type})
    },

    canCancel: function (res){
      var today=  new Date();
      var start =  new Date(res.startDateTime[0], res.startDateTime[1]-1, res.startDateTime[2],
                            res.startDateTime[3], res.startDateTime[4]);
      var time_diff = start - today;
      var days_diff = time_diff / (24*60*60*1000);
      return days_diff > 3;

    },

    cancel: function (res){
      axios
        .post("https://rental-app-6.herokuapp.com"+"/api/reservation/cancelReservation/", res,{headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {const index = this.reservations.indexOf(res);
          this.reservations.splice(index, 1);  this.show('foo-css', 'success')}).catch(function error(error) {
        alert(error.response.data);
      });
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
