<template>
  <section class="profile_main vh-100" >
    <admin-navbar></admin-navbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: white;  height: 100% ">
                <div class="card-body p-5">

                  <h1 class="title">Business Report</h1>
                  <hr />
                  <div class="row">

                    <div class="col-10 p-2">
                      <div class="row p-2">
                        <div class="col-4">
                          <label class="label">Start date:</label>
                          <div>
                            <input class="" ref="start_date_input" type="date" placeholder="Start date input" />
                          </div>
                        </div>
                        <div class="col-4">
                          <label class="label">End date:</label>
                          <div>
                            <input class="" ref="end_date_input" type="date" placeholder="End date input" />
                          </div>
                        </div>
                        <div class="col-3 " style="margin-top: 30px">
                          <button v-on:click="search()" id="editButton" class="btn btn-primary edit-button" type="button">Search</button>
                        </div>

                      </div>
                      <div class="row p-3">
                        <table class="table">
                          <thead>
                          <tr style="background:rgba(75, 192, 192, 0.4)">
                            <th colspan="2">Entity </th>
                            <th>Start date</th>
                            <th>End date</th>
                            <th> System profit</th>
                            <th>Advertiser profit</th>
                          </tr>
                          </thead>
                          <tbody>
                          <tr style="background: rgba(75, 192, 192, 0.2);" v-for="reservation in found_reservations">
                            <td colspan="2">{{reservation.entity.name}}</td>
                            <td>{{reservation.startDateTime[2]+"."+reservation.startDateTime[1]+"."+reservation.startDateTime[0]+"."}}</td>
                            <td>{{reservation.endDateTime[2]+"."+reservation.endDateTime[1]+"."+reservation.endDateTime[0]+"."}}</td>
                            <td>{{reservation.systemProfit*reservation.price/100}}</td>
                            <td>{{reservation.advertiserProfit*reservation.price/100}}</td>
                          </tr>
                          </tbody>

                        </table>
                        <p style="text-align: right;padding-right: 100px"><b>System profit sum:</b> {{ sumSystem }}</p>
                        <p style="text-align: right;padding-right: 100px"><b>Advertiser profit sum:</b> {{ sumAdvertiser }}</p>
                      </div>

                    </div>
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
import AdminNavbar from "./header/AdminNavbar";
import axios from "axios";
export default {
  name: "AdminBusinessReport",
  components: {AdminNavbar},
  data(){
    return{
      reservations: [],
      found_reservations:[],
      sumAdvertiser:0,
      sumSystem:0,
    }
  }
  ,mounted:function () {
    this.role = sessionStorage.getItem("role");

    axios
      .get(process.env.VUE_APP_SERVER_PORT + '/api/reservation/findHistory', {
        headers: {
          Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")
        }
      })
      .then(response => {
        this.reservations = response.data
        this.found_reservations = this.reservations;
        this.getSum();
      })

  },
  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Wrong dates!</p>`
      let text = `<p style="font-size: 20px">Enter valid dates!</p>`
      this.$notify({group, title, text, type})
    },
    getSum:function (){
      this.sumSystem=0;
      this.sumAdvertiser=0;
      for(let i=0;i<this.found_reservations.length;i++){
        this.sumAdvertiser += this.found_reservations[i].advertiserProfit * this.found_reservations[i].price/100;
        this.sumSystem += this.found_reservations[i].systemProfit * this.found_reservations[i].price/100;
      }
    },
    search:function (){
      let today = new Date(Date.now());
      let start_date = new Date(this.$refs.start_date_input.value)
      let end_date = new Date(this.$refs.end_date_input.value)
      if(start_date>end_date){
        this.show('foo-css', 'error')
        return;
      }
      if(end_date>today){
        this.show('foo-css', 'error')
        return;
      }
      this.found_reservations = [];
      for(let i=0;i<this.reservations.length;i++){
        let start = new Date(this.reservations[i].startDateTime[0],this.reservations[i].startDateTime[1]-1,this.reservations[i].startDateTime[2],this.reservations[i].startDateTime[3],this.reservations[i].startDateTime[4]);
        let end = new Date(this.reservations[i].endDateTime[0],this.reservations[i].endDateTime[1]-1,this.reservations[i].endDateTime[2],this.reservations[i].endDateTime[3],this.reservations[i].endDateTime[4]);
        if(start.getTime() >= start_date.getTime() && end.getTime() <= end_date.getTime()){
          this.found_reservations.push(this.reservations[i]);
        }
      }
      this.getSum();

    }
  }
}
</script>

<style scoped>

</style>
