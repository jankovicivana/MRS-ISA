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
                    <div class="col-8">
                      <calendar
                        :eventCategories="eventCategories"
                        :events="events"
                        ref="calendar"
                      />
                    </div>
                    <div class="col-4">
                      <form>
                        <div class="form-outline mb-4">
                          <label class="label">Start date:</label>
                          <div>
                            <input class="form-control form-control-lg" ref="start_date_input" type="date"   placeholder="Start date input" />
                          </div>
                        </div>
                        <div class="form-outline mb-4">
                          <label class="label">End date:</label>
                          <div>
                            <input class="form-control form-control-lg" type="date" ref="end_date_input"   placeholder="End date input" />
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

<script>
import axios from "axios";
import { Calendar } from 'vue-sweet-calendar'
import 'vue-sweet-calendar/dist/SweetCalendar.css'
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "InstructorAvailability",
  data() {
    return {
      selectedDate: null,
      periods:'',
      eventCategories: [
        {
          id: 1,
          title: 'InstructorAvailability',
          textColor: 'white',
          backgroundColor: '#2e6b6b'
        },
        {
          id: 2,
          title: 'Company-wide',
          textColor: 'white',
          backgroundColor: 'red'
        },
        {
          id: 3,
          title: 'National',
          textColor: 'white',
          backgroundColor: 'green'
        }
      ],
      events: []
    }
  },
  mounted: function(){

    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/getAvailablePeriod/getInstructor", {headers: {Authorization:
          'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {
        this.periods = response.data
        this.fillCalendar();
      }).catch(function error(error) {
        alert(error.response.data);
      });


  },
  methods:{
    fillCalendar:function (){
      for(let p of this.periods){
        this.newEvent = {
          title: 'Event 1',
          start: p.startDateTime,
          end: p.endDateTime,
          categoryId: 1
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
        title: 'Event 1',
        start: start_date,
        end: end_date,
        categoryId: 1
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
    Calendar // Registering Component
  }
}
</script>

<style scoped>

</style>
