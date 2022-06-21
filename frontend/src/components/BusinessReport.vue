<template>
  <section class="profile_main min-vh-100" >
    <fishing-instructor-navbar v-if="role === 'ROLE_fishingInstructor'"></fishing-instructor-navbar>
    <cottage-owner-navbar v-if="role === 'ROLE_cottageOwner'"></cottage-owner-navbar>
    <boat-owner-navbar v-if="role === 'ROLE_boatOwner'"></boat-owner-navbar>
    <AdminNavbar v-if="role === 'ROLE_admin'" :isAdmin="true"></AdminNavbar>
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
                    <p class="col-3 pt-4" v-if="role === 'ROLE_cottageOwner'">Average cottage grade: </p>
                    <p class="col-3 pt-4" v-if="role === 'ROLE_boatOwner'">Average boat grade: </p>
                    <p class="col-3 pt-4" v-if="role === 'ROLE_fishingInstructor'">Average adventure grade: </p>
                    <star-rating  v-if="role !== 'ROLE_admin'"  class="col-9" :rating="average_grade" :read-only="true" :increment="0.01" :star-size="20" :size="200"></star-rating>

                  </div>
                  <div class="row">

                    <div class="col-5 ">
                      <select id="chart_input" ref="chart_input" class="form-select" style="width: 130px" v-on:change="changeChart()" required>
                        <option>Weekly</option>
                        <option selected>Monthly</option>
                        <option>Yearly</option>
                      </select>
                      <canvas id="myChart" width="200" height="200"></canvas>
                    </div>
                    <div class="col-7 p-2">
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
                            <th>Start date</th>
                            <th>End date</th>
                            <th>Profit</th>

                          </tr>
                          </thead>
                          <tbody>
                          <tr style="background: rgba(75, 192, 192, 0.2);" v-for="reservation in found_reservations">
                            <td>{{reservation.startDateTime[2]+"."+reservation.startDateTime[1]+"."+reservation.startDateTime[0]+"."}}</td>
                            <td>{{reservation.endDateTime[2]+"."+reservation.endDateTime[1]+"."+reservation.endDateTime[0]+"."}}</td>
                            <td  v-if="role !== 'ROLE_admin'">{{reservation.advertiserProfit}}</td>
                            <td  v-if="role === 'ROLE_admin'">{{reservation.systemProfit}}</td>
                          </tr>
                          </tbody>

                        </table>
                        <p style="text-align: right;padding-right: 100px"><b>Sum:</b> {{ sum }}</p>
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
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";
import axios from "axios";
import Bar from 'vue-chartjs'
import AdminNavbar from "./header/AdminNavbar";


export default {
  name: "BusinessReport",
  components: {AdminNavbar, BoatOwnerNavbar, CottageOwnerNavbar, FishingInstructorNavbar,Bar},
  data(){
    return{
      reservations: [],
      found_reservations:[],
      role:'',
      average_grade:0,
      sum:0
    }
  }
  ,mounted:function () {
    this.role = sessionStorage.getItem("role");
    let reservationUrl = '';
    let gradeUrl = '';
    if (this.role === "ROLE_cottageOwner") {
      reservationUrl = "/api/reservation/findHistoryByCottageOwner";
      gradeUrl = "/api/cottages/getAverageGrade";
    }else if(this.role === "ROLE_boatOwner"){
      reservationUrl = "/api/reservation/findHistoryByBoatOwner";
      gradeUrl = "/api/boats/getAverageGrade";
    }else if (this.role === "ROLE_fishingInstructor"){
      reservationUrl = "/api/reservation/findHistoryByInstructor";
      gradeUrl = "/api/adventures/getAverageGrade";
    }else if (this.role === "ROLE_admin"){
      reservationUrl = "/api/reservation/findHistory";
    }
      axios
        .get("https://rental-app-6.herokuapp.com" + reservationUrl, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => {
          this.reservations = response.data
          this.found_reservations = this.reservations;
          this.getSum();
          let data = this.monthReservations(this.reservations);
          this.makeChart(data,['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October','November','December'])
        })

    if (this.role !== "ROLE_admin"){
      axios
        .get("https://rental-app-6.herokuapp.com" + gradeUrl, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (
          this.average_grade = response.data
        ))}





  },
  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Wrong dates!</p>`
      let text = `<p style="font-size: 20px">Enter valid dates!</p>`
      this.$notify({group, title, text, type})
    },
    makeChart:function (data,labels){
      var ctx = document.getElementById('myChart').getContext('2d');
      var myChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [{
            label: 'Reservations',
            data: data,
            backgroundColor:'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }]
        },
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              }
            }]
          }
        }
      });
    },
    yearReservation:function (){
      let data = [0,0,0,0,0];
      for(let i = 0; i < this.reservations.length; i++){
        console.log(this.reservations[i].startDateTime[0])
        data[this.reservations[i].startDateTime[0]-2018]++;
      }
      console.log(data)
      return data;
    },
    monthReservations:function (reservations){
      let data = [0,0,0,0,0,0,0,0,0,0,0,0];
      for(let i = 0; i < reservations.length; i++){
        console.log(reservations[i].startDateTime)
        data[reservations[i].startDateTime[1]-1]++;
      }
      console.log(data)
      return data;
    },
    weekReservations:function (){
      let data = [0,0,0,0];
      let firstWeek = new Date(Date.now()-24*60*60*1000*7);
      for(let i = 0; i < this.reservations.length; i++){
        console.log(this.reservations[i].startDateTime)
        let day = new Date(this.reservations[i].startDateTime[0],this.reservations[i].startDateTime[1]-1,this.reservations[i].startDateTime[2])
        if(day.getTime() > firstWeek.getTime()){
          data[3]++;
        }else if(day.getTime() <= firstWeek.getTime() && day.getTime() > firstWeek.getTime()-24*60*60*1000*7){
          data[2]++
        }else if(day.getTime() <= firstWeek.getTime()-24*60*60*1000*7 && day.getTime() > firstWeek.getTime()-24*60*60*1000*7*2){
          data[1]++;
        }else if(day.getTime() <= firstWeek.getTime()-24*60*60*1000*7*2 && day.getTime() > firstWeek.getTime()-24*60*60*1000*7*3){
          data[0]++;
        }
      }
      console.log(data)
      return data;
    },
    changeChart:function (){
      let period = this.$refs.chart_input.value;
      let months = ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October','November','December'];
      let years = ['2018', '2019', '2020', '2021', '2022'];
      let weeks = [];
      for(let i=3;i>=0;i--){
        weeks.push(new Date(Date.now()-24*60*60*1000*7*i).toLocaleDateString())
      }
      if(period === 'Yearly'){
        this.makeChart(this.yearReservation(),years);
      }else if(period === 'Monthly'){
        this.makeChart(this.monthReservations(this.reservations),months);
      }else if(period === 'Weekly'){
        this.makeChart(this.weekReservations(),weeks);
      }
    },
    getSum:function (){
      this.sum=0;
      for(let i=0;i<this.found_reservations.length;i++){
        if (this.role !== "ROLE_admin"){
        this.sum += this.found_reservations[i].advertiserProfit;}else{
          this.sum += this.found_reservations[i].systemProfit;
        }
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
