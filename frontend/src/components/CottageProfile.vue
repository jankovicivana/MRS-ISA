<template>
  <div>
    <CottageOwnerNavbar v-if="role === 'ROLE_cottageOwner'"></CottageOwnerNavbar>
    <MainNavbar v-if="role === null || role==='null'"></MainNavbar>
    <ClientNavbar v-if="role === 'ROLE_client'"></ClientNavbar>
    <AdminNavbar v-if="role === 'ROLE_admin'" :isAdmin="true"></AdminNavbar>
    <section class="profile_main py-lg-3 min-vh-100">

    <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
      <router-link v-if="role === 'ROLE_cottageOwner'" class="col-1 rounded-pill" :to="{ name: 'UpdateCottage',id:cottage.id }" style="background: #2e6b6b;margin: 5px;border: none;color: white" tag="button">Edit</router-link>
      <button v-if="role === 'ROLE_cottageOwner'" type="button" class="col-1 rounded-pill " v-on:click="deleteCottage()" style="background: #2e6b6b;border: none;margin: 5px;color: white">Delete</button>
    </div>
    <div class="container cottage_profile px-4 px-lg-5 my-5">
      <div class="row align-items-center pt-5">
        <div class="col-md-6">
          <carousel :per-page="1" :navigationEnabled="true" :mouse-drag="false" :autoplay="true" v-bind:loop="true" v-bind:speed="3000">
            <slide v-for="url in imagesUrl">
              <img class="d-block w-100" :src="url" alt="First slide" style="height: 400px">
            </slide>

          </carousel>
          <div class="row thumbs pt-3 ">
            <span v-for="url in imagesUrl" class="side_photo col-3 px-1" style="padding-top: 10px"><img :src="url" alt="Cottage photo1" class="img-responsive" width="130px" height="130px" style="width: 130px; height: 130px"></span>
          </div>
        </div>
        <div class="col-md-6" >
          <div class="row mx-2 mb-6">
            <div class="col-9 fw-bolder" style="font-size: 35px">{{cottage.name}}</div>
            <star-rating class="col-3" :rating=cottage.averageGrade :read-only="true" :increment="0.1" :star-size="25" :size="200" ></star-rating>
          </div>

          <div class="fs-5 m-3 row">
            <div class="col-6 p-2">
              Price: $<span>{{cottage.price}}</span>
            </div>
            <div class="col-6" style="position: relative;float: right;display: inline-block ">
              <p class="col-12 p-2" style="text-align: center;border-radius: 45%;background: #2e6b6b;color: white;font-size: 18px">Cottage owner: {{cottage.cottageOwnerName}}</p>
            </div>
          </div>


          <p class="lead p-3">{{cottage.description}}</p>
          <div class="row number_info p-3">
            <p class="col-2" > <font-awesome-icon icon="fa-solid fa-user-friends"/> {{cottage.maxNumPerson}}</p>
            <p class="col-2"><font-awesome-icon icon="fa-solid fa-home"/> {{num_rooms}}</p>
            <p class="col-2"><font-awesome-icon icon="fa-solid fa-bed"/> {{num_beds}}</p>
          </div>
          <div class="row p-3">
            <p style="font-size: 25px">Additional services</p>
            <div class="row services">
              <p class="col-5" v-for="as in cottage.additionalServices"><font-awesome-icon class="small-icon" icon="fa-solid fa-check-circle" /> {{as.name}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="row py-5">
        <div class="col-3 my-4 mr-4" style="background: #f8f2ec;border-radius: 5%">
          <p style="font-size: 25px;">Rules</p>
          <div class="rules">
            <p v-for="r in cottage.rules"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.rule}}</p>
          </div>
        </div>
        <div class="col-1" style="width: 50px"></div>
        <div class="col-8 py-4" style="background: #f8f2ec;border-radius: 5%">
          <h4><b>Location:</b>  {{address.country}}, {{address.city}}, {{address.street}}</h4>
          <l-map style="height: 300px" :zoom="zoom" :center="center">
            <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
            <l-marker :lat-lng="markerLatLng"></l-marker>
          </l-map>
        </div>
      </div>



      <div class="row " v-if="quick.length != 0" >
        <div class="col-12" style="background: #f8f2ec;">
          <div class="row pt-3">
            <h3 id="quick_heading" class="col-10">Quick reservations - enormous discounts!</h3>
            <span class="col-2" style="float: right;background: #f8f2ec;">
            <button type="button" v-on:click="showModal()" style="color: white;background: #c91d1d;" class="btn btn-info btn-lg " v-if="this.role === 'ROLE_cottageOwner'">Add new</button>
            <AddQuickReservation
              :id="cottage.id"
              style="width: 300px"
              v-show="isModalVisible"
              v-on:click="closeModal()"
            />
            </span>
          </div>
          <div v-if="quick.length === 0">
            <h4 class="p-3">There are no quick reservations for now.</h4>
          </div>

          <div class="row p-3">
            <div class="col-4 p-3 m-2 quick_res zoom" v-for="q in quick" v-if="!q.isReserved">
              <div>
                <h4 class="res_date">{{q.startDateTime[2]+"."+q.startDateTime[1]+"."+q.startDateTime[0]+"."}} - {{q.endDateTime[2]+"."+q.endDateTime[1]+"."+q.endDateTime[0]+"."}}</h4>
                <div class="discount">{{q.discount}}%</div>
              </div>
              <p class="py-2"><font-awesome-icon icon="fa-solid fa-user-friends"/> {{q.maxPersonNum}}</p>
              $<span class="text-decoration-line-through">{{q.price}}</span>
              $<span class="before_price">{{q.discountedPrice}}</span>
              <div class="quick_res_btn"><button type="button" class="btn" v-on:click="reserve(q.id)">RESERVE</button></div>
            </div>

          </div>

        </div>
      </div>


      <hr />

      <div class="row">
        <h3 v-if="role === 'ROLE_cottageOwner'">Add availability</h3>
        <div class="col-7 m-6" >
          <full-calendar id="calendar"  @eventClick="viewEvent" :events="events"   locale="en"></full-calendar>
        </div>
        <div class="col-4 mt-3">
          <div class="p-3">
            <h5>Info</h5>
            <hr/>
            <span style="color: green">● </span>- Available period <br/>
            <span style="color: red">● </span>- Reservation period<br/>
            <span style="color: blue">● </span>- Discount period <br/>


          </div>
          <br/><br/><br/>
          <h6 v-if="role === 'ROLE_cottageOwner'">Add new available period</h6>
          <hr v-if="role === 'ROLE_cottageOwner'">
          <form v-if="role === 'ROLE_cottageOwner'">
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
              <button type="button" v-if="role === 'ROLE_cottageOwner'"  v-on:click="addAvailablePeriod()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
            </div>
          </form>
        </div>
      </div>


    </div>
  </section>
  </div>
</template>

<script>
import axios from "axios";
import AddQuickReservation from "./AddQuickReservation";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
import router from "../router";
import MainNavbar from "./header/MainNavbar";
import ClientNavbar from "./header/ClientNavbar";
import AdminNavbar from "./header/AdminNavbar";

export default {
  name: "CottageProfile",
  components:{
    AdminNavbar,
    ClientNavbar,
    MainNavbar,
    CottageOwnerNavbar,
    AddQuickReservation,
    'full-calendar': require('vue-fullcalendar')

  },
  data: function (){
    return{
      cottage: '',
      quick:[],
      num_rooms: 0,
      num_beds: 0,
      isModalVisible: false,
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution: '',
      zoom: 15,
      center: [0,0],
      markerLatLng: [0, 0],
      address:'',
      cottageId: this.$route.params.id,
      config:'',
      selectedDate: null,
      events: [],
      role:'',
      imagesUrl:[],
      today:''
    }
    },
      mounted:function (){
      this.today = new Date(Date.now());
      document.getElementById("calendar").setAttribute('height', '400');
      const sumFuncy = async (a,b) => a+b;
      this.role = sessionStorage.getItem("role");
      window.scrollTo(0, 0)

        axios
          .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/"+this.cottageId)
          .then(first_response => {
            this.cottage = first_response.data
            this.address=first_response.data.address
            first_response.data.images.forEach(image => {
              this.loadImage(image.path);
            });
            first_response.data.quickReservations.forEach(q => {
              let date = new Date(q.expirationDateTime[0],q.expirationDateTime[1]-1,q.expirationDateTime[2])
              if(date > this.today){
                this.quick.push(q);
              }
            });
            this.num_rooms=first_response.data.rooms.length
            first_response.data.rooms.forEach(async (room) => {this.num_beds=await sumFuncy(this.num_beds,room.bedNumber)})
            this.config = {
                method: 'get',
                url: 'https://api.geoapify.com/v1/geocode/search?text='+this.address.street+' '+this.address.city+' '+this.address.postal_code+' '+this.address.country+'&apiKey=edff0ba2d6d545279a82d4d37402a851',
                headers: { }
              }
            axios(this.config).then(second_response => {
              this.center = [second_response.data.features[0].geometry.coordinates[1],second_response.data.features[0].geometry.coordinates[0]]
              this.markerLatLng = [second_response.data.features[0].geometry.coordinates[1],second_response.data.features[0].geometry.coordinates[0]]
            })
          })

        axios.get(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/getAvailablePeriod/"+this.cottageId)
          .then(response => {
            this.periods = response.data
            this.fillCalendar(this.periods,'bg-success');
          }).catch(function error(error) {
          alert(error.response.data);
        });
        axios.get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findAllOfCottage/"+this.cottageId)
          .then(response => {
            this.reservations = response.data
            this.fillCalendar(this.reservations,'bg-danger');
          }).catch(function error(error) {
          alert(error.response.data);
        });
        axios.get(process.env.VUE_APP_SERVER_PORT+"/api/quickReservation/findQuickReservationBy/"+this.cottageId)
          .then(response => {
            this.discounts = response.data
            this.fillCalendar(this.discounts,'');
          }).catch(function error(error) {
          alert(error.response.data);
        });

    },methods: {
    viewEvent:function (event, jsEvent, pos){
      let info = event["YOUR_DATA"]["timeInfo"]
      this.show('foo-css', 'success',`<p style="font-size: 25px">Time info</p>`,`<p style="font-size: 20px">`+info+`</p>`)
      setTimeout(() => { }, 3000)      },

    fillCalendar:function (listElements,style){
      for(let p of listElements){
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
      showModal:function() {
        this.isModalVisible = true;
      },
      closeModal:function() {
        this.isModalVisible = false;
      },
      deleteCottage:function (){
        let id = this.cottage.id
        axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/cottages/deleteCottage/"+id, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
          .then(response => {
            this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully deleted!</p>`,`<p style="font-size: 20px">Successfully deleted cottage!</p>`)
            setTimeout(() => {this.$router.push({name:"HomepageCottageOwner"}); }, 3000)
          }).catch((error) => {
            this.show('foo-css', 'error',`<p style="font-size: 25px">Deletion is not possible!</p>`,`<p style="font-size: 20px">Cottage has reservations.</p>`)
        });

      },
    loadImage(name) {
      axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+name,{responseType:"blob"})
        .then(response => {
          this.imagesUrl.push(URL.createObjectURL(response.data));
        })
        .catch((error) =>{
          console.log(error);
        });
    },
    addAvailablePeriod:function (){
        let start_date = this.$refs.start_date_input.value
        let end_date = this.$refs.end_date_input.value
      if(start_date == ''){
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter start date!</p>`)
        return;
      }
      if(end_date == ''){
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter end date!</p>`)
        return;
      }
      if(start_date>end_date){
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">End date must be after start date!</p>`)
        return;
      }
      if (start_date<Date.now || end_date<Date.now){
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter future dates!</p>`)
        return;
      }

      if (!this.isPeriodAvailable(start_date,end_date)){
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You have entered this available period!</p>`)
        return;
      }

        this.info = {
          startDateTime: start_date,
          endDateTime: end_date,
          entity:this.cottageId
        };
      this.periods.push(this.info)
      this.newEvent = {
          title: '',
          start: start_date,
          end: end_date,
          cssClass:'bg-success',
          YOUR_DATA : {
            timeInfo: start_date +" "+start_date.split('T')[1].substring(0,5)+"-"+end_date+" "+end_date.split('T')[1].substring(0,5)
          }
        }
        this.events.push(this.newEvent);

        axios.post(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/addCottage",this.info, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
          .then(response => {
            this.show('foo-css', 'success','`<p style="font-size: 25px">Successfully added!</p>','`<p style="font-size: 20px">Successfully added available period.</p>')
            setTimeout(() => { }, 3000)
            //this.$router.push({name:"CottageProfile",params:{id:this.cottageId}});
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

    },
    reserve:function(id) {

      console.log(id);
      axios.put(process.env.VUE_APP_SERVER_PORT+"/api/reservation/makeReservationFromQuick/"+id, {},{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully reserved!</p>`,`<p style="font-size: 20px">Successfully reserved quick reservation.</p>`)
          setTimeout(() => { location.reload(); }, 2000)
        }).catch(error => {
          if(!error.response || error.response.status === 403){
            this.show('foo-css', 'error',`<p style="font-size: 25px">Forbidden Error!</p>`,`<p style="font-size: 20px">You can not make quick reservation.</p>`)
          }
      });
    }

  }
}
</script>

<style scoped>
  .fa{
    font-size: 8px;
    color: #00b3b3;
    alignment: center;
  }
  .small-icon{
    font-size: 15px;
  }
</style>
