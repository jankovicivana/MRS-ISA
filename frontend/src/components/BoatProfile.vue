<template>
  <div>
    <BoatOwnerNavbar  v-if="role === 'ROLE_boatOwner'"></BoatOwnerNavbar>
    <MainNavbar v-if="role === null"></MainNavbar>
    <ClientNavbar v-if="role === 'ROLE_client'"></ClientNavbar>
  <section class="profile_boat py-lg-3" >
    <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
      <router-link v-if="role === 'ROLE_boatOwner'" class="col-1 rounded-pill" :to="{ name:'UpdateBoat',id:boat.id}" style="background: #2e6b6b;margin: 5px;color: white;border-color: white" tag="button">Edit</router-link>
      <button v-if="role === 'ROLE_boatOwner'" type="button" class="col-1 rounded-pill" v-on:click="deleteBoat()" style="background:#2e6b6b;margin: 5px;color: white;border-color: white">Delete</button>
    </div>
    <div class="container boat_profile px-4 px-lg-5 my-5">
      <div class="row align-items-center">
        <div class="col-md-6">

          <carousel :per-page="1" :navigationEnabled="true" :mouse-drag="false" :autoplay="true" :adjustable-height="true" v-bind:loop="true" v-bind:speed="3000">
            <slide  v-for="url in imagesUrl" >
              <img class="d-block w-100" :src="url"  alt="First slide" style="border-radius: 2%">
            </slide>
          </carousel>

          <div class="row thumbs pt-3 ">
            <span v-for="url in imagesUrl" class="side_photo col-3 px-1" style="padding-top: 10px;"><img :src="url" alt="Boat photo" class="img-responsive" width="130px" height="130px"></span>
          </div>
        </div>
        <div class="col-md-6 pt-5" >
          <div class="row m-2">
            <h1 class="col-9 " >{{boat.name}}</h1>
            <star-rating class="col-3" :rating="5" :read-only="true" :increment="0.01" :star-size="25" :size="200"></star-rating>
          </div>

          <div class="fs-5 m-3 row">
            <div class="col-7 p-2">
              Price: $<span>{{boat.price}}</span>
            </div>
            <div class="col-5 p-2" style="position: relative;float: right;display: inline-block ">
              <p class="col-12 p-2" style="text-align: center;border: 2px solid royalblue;border-radius: 47%;background:cornflowerblue;color: white;">Boat owner: {{boat.boatOwnerName}}</p>
            </div>
          </div>
          <p class="lead p-3">{{boat.description}}</p>
          <hr style="color: blue" />
          <div class="row number_info py-3">
            <p class="col-2 " style="padding-left: 30px;" > <font-awesome-icon icon="fa-solid fa-user-friends"/> {{boat.capacity}}</p>
            <p class="col-3" >Length: {{boat.length}}m</p>
            <p class="col-4" >Motor number: {{boat.motorNum}}</p>
            <p class="col-3" >Power: {{boat.power}}HS</p>
            <p class="col-5" style="margin-left: 100px" >Max speed: {{boat.maxSpeed}}km/h</p>
            <p class="col-4" >Type: {{boat.type}}</p>
          </div>

          <hr style="color: blue" />
          <div class="row p-3">
            <div class="col-12">
              <p style="font-size: 25px">Additional services</p>
              <div class="row services">
                <p class="col-5" v-for="as in boat.additionalServices"><font-awesome-icon class="small-icon" icon="fa-solid fa-check-circle" /> {{as.name}}</p>
              </div>
            </div>


          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-3 my-4 p-3" style="background:aliceblue;border-radius: 5%">
          <p style="font-size: 25px;">Rules</p>
          <div class="rules">
            <p v-for="r in boat.rules"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.rule}}</p>
          </div>
        </div>
        <div class="col-1" style="width: 50px"></div>
        <div class="col-8  py-4"  style="background:aliceblue;border-radius: 5%">
          <h4><b>Location:</b>  {{address.country}}, {{address.city}}, {{address.street}}</h4>
          <l-map style="height: 300px" :zoom="zoom" :center="center">
            <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
            <l-marker :lat-lng="markerLatLng"></l-marker>
          </l-map>
        </div>
      </div>
      <div class="row py-2">
        <div class="col-5 my-4 p-3" style="background:aliceblue;border-radius: 5%;">
          <p style="font-size: 25px;">Fishing equipment</p>
          <div class="rules">
            <p v-for="fe in boat.fishingEquipment"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{fe.equipment}}</p>
          </div>
        </div>
        <div class="col-1"></div>
        <div class="col-5 my-4 p-3" style="background:aliceblue;border-radius: 5%">
          <p style="font-size: 25px;">Navigation equipment</p>
          <div>
            <p v-for="ne in boat.navigationEquipment"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{ne.equipment}}</p>
          </div>
        </div>
      </div>



      <div class="row" >
        <div class="col-12" style="background: aliceblue">
          <div class="row pt-3" style="padding-left: 10px;">
            <h3 id="quick_heading" class="col-10">Quick reservations - enormous discounts!</h3>
            <span class="col-2" style="float: right;">
            <button v-if="role === 'ROLE_boatOwner'" type="button" v-on:click="showModal()" style="color: white;background: #c91d1d;" class="btn btn-info btn-lg ">Add new</button>
            <AddQuickReservation
              :id="boat.id"
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
              <div class="quick_res_btn"><button type="button" v-on:click="reserve(q.id)" class="btn">RESERVE</button></div>
            </div>

          </div>

        </div>
      </div>


      <hr />

      <div class="row" style="background: aliceblue">
        <h3 v-if="role === 'ROLE_boatOwner'">Add availability</h3>
        <div class="col-7 p-4" >
          <full-calendar id="calendar" :events="events"  @eventClick="viewEvent" locale="en"></full-calendar>
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
          <h6 v-if="role === 'ROLE_boatOwner'">Add new available period</h6>
          <hr v-if="role === 'ROLE_boatOwner'">
          <form v-if="role === 'ROLE_boatOwner'">
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
  </section>
  </div>
</template>

<script>
import axios from "axios";
import AddQuickReservation from "./AddQuickReservation";
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
import MainNavbar from "./header/MainNavbar";
import ClientNavbar from "./header/ClientNavbar";

export default {
  name: "BoatProfile",
  components:{
    ClientNavbar,
    MainNavbar,
    BoatOwnerNavbar,
    AddQuickReservation,
    'full-calendar': require('vue-fullcalendar')
  },
  data: function (){
    return{
      boat: '',
      quick:[],
      isModalVisible: false,
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution: '',
      zoom: 15,
      center: [0,0],
      markerLatLng: [0, 0],
      address:'',
      boatId: this.$route.params.id,
      config:'',
      events: [],
      role:'',
      imagesUrl:[]
    }
  },
  mounted:function (){
    this.role = sessionStorage.getItem("role");
    window.scrollTo(0, 0)
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/boats/"+this.boatId)
      .then(response => {
        this.boat = response.data
        this.address=response.data.address
        response.data.images.forEach(image => {
          this.loadImage(image.path);
        });
        response.data.quickReservations.forEach(q => {
          let date = new Date(q.expirationDateTime[0],q.expirationDateTime[1]-1,q.expirationDateTime[2])
          if(date > this.today){
            this.quick.push(q);
          }
        });
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

    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/getAvailablePeriod/"+this.boatId)
      .then(response => {
        this.periods = response.data
        this.fillCalendar(this.periods,'bg-success');
      }).catch(function error(error) {
      alert(error.response.data);
    });
    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findAllOfBoat/"+this.boatId)
      .then(response => {
        this.reservations = response.data
        this.fillCalendar(this.reservations,'bg-danger');
      }).catch(function error(error) {
      alert(error.response.data);
    });
    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/quickReservation/findQuickReservationBy/"+this.boatId)
      .then(response => {
        this.discounts = response.data
        this.fillCalendar(this.discounts,'');
      }).catch(function error(error) {
      alert(error.response.data);
    });


  },
  methods:{
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
    loadImage(name) {
      axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+name,{responseType:"blob"})
        .then(response => {
          this.imagesUrl.push(URL.createObjectURL(response.data));
        })
        .catch((error) =>{
          console.log(error);
        });
    },
    show: function(group, type='',titleMessage,text){
      let title = titleMessage
      this.$notify({group, title, text, type})
    },
    showModal:function() {
      this.isModalVisible = true;
    },
    closeModal:function() {
      this.isModalVisible = false;
    },
    deleteBoat:function (){
      let id = this.boat.id
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/boats/deleteBoat/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully deleted!</p>`,`<p style="font-size: 20px">Successfully deleted boat!</p>`)

        }).catch((error) => {
        //alert(error.response.data);
          this.show('foo-css', 'error',`<p style="font-size: 25px">Deletion is not possible!</p>`,`<p style="font-size: 20px">Boat has reservations.</p>`)
      });
    },
  addAvailablePeriod:function (){
    let start_date = this.$refs.start_date_input.value
    let end_date = this.$refs.end_date_input.value
    if(start_date === ''){
      alert("You must enter start date!")
      return;
    }
    if(end_date === ''){
      alert("You must enter end date!")
      return;
    }
    if(start_date>end_date){
      alert("End date must be after start date.")
      return;
    }

    this.info = {
      startDateTime: start_date,
      endDateTime: end_date,
      entity:this.boatId
    };
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

    axios.post(process.env.VUE_APP_SERVER_PORT+"/api/availablePeriod/addBoat",this.info, {headers: {Authorization:
          'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {
        this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully added!</p>`,`<p style="font-size: 20px">Successfully added quick reservation!</p>`)
        setTimeout(() => { }, 3000)
      }).catch(function error(error) {
      alert(error.response.data);
    });
  },
    reserve:function(id) {
      console.log(id);
      axios.put(process.env.VUE_APP_SERVER_PORT+"/api/reservation/makeReservationFromQuick/"+id, {},{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully reserved!</p>`,`<p style="font-size: 20px">Successfully reserved quick reservation.</p>`)
          setTimeout(() => { location.reload(); }, 2000)
        }).catch(function error(error) {
        alert(error.response.data);
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
