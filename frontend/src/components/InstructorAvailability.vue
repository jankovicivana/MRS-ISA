<template>
  <section class="profile_main  min-vh-100 is-fullheight" >
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
                      <full-calendar id="calendar" :events="events"  @eventClick="viewEvent"  locale="en"></full-calendar>

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
                          <button type="button"  v-on:click="addAvailablePeriod()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
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

    //document.getElementById('calendar').dispatchEvent('eventClick',event, jsEvent, pos)
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

      viewEvent:function (event, jsEvent, pos){
        let info = event["YOUR_DATA"]["timeInfo"]
        this.show('foo-css', 'success',`<p style="font-size: 25px">Time info</p>`,`<p style="font-size: 20px">`+info+`</p>`)
        setTimeout(() => { }, 3000)      },
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
          title: '',
          start: p.startDateTime[0]+'-'+p.startDateTime[1]+'-'+p.startDateTime[2],
          end: p.endDateTime[0]+'-'+p.endDateTime[1]+'-'+p.endDateTime[2],
          cssClass:style,
          YOUR_DATA : {
            timeInfo: p.startDateTime[2]+'/'+p.startDateTime[1]+'/'+p.startDateTime[0] +" "+p.startDateTime[3]+':'+p.startDateTime[4]+"-"+p.endDateTime[2]+'/'+p.endDateTime[1]+'/'+p.endDateTime[0]+" "+p.endDateTime[3]+':'+p.endDateTime[4]
          }
        }
        this.events.push(this.newEvent);
      }
    },
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },

    addAvailablePeriod:function (){
      let start_date = this.$refs.start_date_input.value
      let end_date = this.$refs.end_date_input.value
      if(start_date == ''){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter start date!</p>`)
        return;
      }
      if(end_date == ''){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter end date!</p>`)
        return;
      }
      if(start_date>end_date){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">End date must be after start date!</p>`)
        return;
      }


      if (!this.isPeriodAvailable(start_date,end_date)){
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You have entered this available period!</p>`)
        return;
      }

      this.info = {
        startDateTime: start_date,
        endDateTime: end_date
      };
      this.periods.push(this.info)
      this.newEvent = {
        start: start_date,
        end: end_date,
        YOUR_DATA : {
          timeInfo: start_date +" "+start_date.split('T')[1].substring(0,5)+"-"+end_date+" "+end_date.split('T')[1].substring(0,5)
        },
        cssClass:'bg-success'
      }
      this.events.push(this.newEvent);

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/addForFishingInstructor",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully added!</p>`,`<p style="font-size: 20px">Successfully added available period!</p>`)
          setTimeout(() => { }, 3000)
        }).catch(function error(error) {
        alert(error.response.data);
      });


    },
    isPeriodAvailable:function (start_date,end_date){
        for(let p of this.periods){
            if (start_date >= p.startDateTime && end_date<= p.endDateTime){
              return false;
            }
        }
        return true;

    }
  },
  components: {
    FishingInstructorNavbar,
    'full-calendar': require('vue-fullcalendar')
  }
}


</script>

<style scoped>

  .profile_main{
    height: fit-content;
  }
</style>
