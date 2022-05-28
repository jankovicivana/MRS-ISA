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

                  <h1 class="title">Fishing instructor availability</h1>
                  <hr />
                  <div class="row">
                    <div class="col-8" id="calendar">
                      <full-calendar id="calendar" :events="events"   locale="en"></full-calendar>

                    </div>
                    <div class="col-4">
                      <div>
                        <h6>Info</h6>
                        <hr/>
                        <span style="color: green">● </span>- Available period <br/>
                        <span style="color: red">● </span>- Reservation period<br/>
                        <span style="color: blue">● </span>- Discount period <br/>


                      </div>
                      <br/><br/><br/>
                      <h6>Add new available period</h6>
                      <hr>
                      <form>
                        <div class="form-outline mb-4">
                          <label class="label">Start date:</label>
                          <div>
                            <input class="form-control form-control-lg" ref="start_date_input" type="datetime-local"   placeholder="Start date input" />
                          </div>
                        </div>
                        <div class="form-outline mb-4">
                          <label class="label">End date:</label>
                          <div>
                            <input class="form-control form-control-lg" type="datetime-local" ref="end_date_input"   placeholder="End date input" />
                          </div>
                        </div>
                        <div class="d-flex justify-content-center">
                          <button type="submit"  v-on:click="addAvailablePeriod()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
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
    </div>
  </section>
</template>

<script type="text/javascript">
import axios from "axios";


import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "InstructorAvailability",
  data() {
    return {
      selectedDate: null,
      periods:'',
      reservations :'',
      discounts:'',
      events: [ ]


    }
  },
  mounted: function(){

    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/getAvailablePeriod/getInstructor", {headers: {Authorization:
          'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {
        this.periods = response.data
        this.fillCalendar(this.periods,'bg-success');
      }).catch(function error(error) {
        alert(error.response);
      });


    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findAllByUser/getInstructor", {headers: {Authorization:
          'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {
        this.reservations = response.data
        this.fillCalendar(this.reservations,'bg-danger');
      }).catch(function error(error) {
      alert(error.response);
    });

    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/quickReservation/findQuickReservationBy/getInstructor", {headers: {Authorization:
          'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {
        this.discounts = response.data
        console.log(this.discounts)
        this.fillCalendar(this.discounts,'');
      }).catch(function error(error) {
      alert(error.response);
    });


  },
  methods:{

      fillCalendar:function (elements,style){
      for(let p of elements){
        for (let i in p.startDateTime){
          if(p.startDateTime[i]<10){
            p.startDateTime[i]= '0' + p.startDateTime[i];
          }
        }
        for (let i in p.endDateTime){
          if(p.endDateTime[i]<10){
            p.endDateTime[i]= '0' + p.endDateTime[i];
          }
        }
        this.newEvent = {
          title: p.startDateTime[3]+':'+p.startDateTime[4]+'-'+p.endDateTime[3]+':'+p.endDateTime[4],
          start: p.startDateTime[0]+'-'+p.startDateTime[1]+'-'+p.startDateTime[2],
          end: p.endDateTime[0]+'-'+p.endDateTime[1]+'-'+p.endDateTime[2],
          cssClass:style
        }
        this.events.push(this.newEvent);
      }
    },
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully added!</p>`
      let text = `<p style="font-size: 20px">Successfully added available period!</p>`
      this.$notify({group, title, text, type})
    },

    addAvailablePeriod:function (){
      let start_date = this.$refs.start_date_input.value
      let end_date = this.$refs.end_date_input.value
      if(start_date == ''){
        alert("You must enter start date!")
        return;
      }
      if(end_date == ''){
        alert("You must enter end date!")
        return;
      }
      if(start_date>end_date){
        alert("End date must be after start date.")
        return;
      }

      this.info = {
        startDateTime: start_date,
        endDateTime: end_date
      };
      this.newEvent = {
        title: start_date.split('T')[1].substring(0,5)+'-'+end_date.split('T')[1].substring(0,5),
        start: start_date,
        end: end_date,
        cssClass:'bg-success'
      }
      this.events.push(this.newEvent);

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/addForFishingInstructor",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success')
          setTimeout(() => { }, 3000)
        }).catch(function error(error) {
        alert(error.response.data);
      });


    }
  },
  components: {
    FishingInstructorNavbar,
    'full-calendar': require('vue-fullcalendar')
  }
}


</script>

<style scoped>

</style>
