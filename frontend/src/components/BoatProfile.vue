<template>
  <div>
    <BoatOwnerNavbar></BoatOwnerNavbar>
  <section class="profile_boat py-lg-3" >
    <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
      <router-link class="col-1 rounded-pill" :to="{ name:'UpdateBoat',id:boat.id}" style="background: #2e6b6b;margin: 5px;color: white;border-color: white" tag="button">Edit</router-link>
      <button type="button" class="col-1 rounded-pill" v-on:click="deleteBoat()" style="background:#2e6b6b;margin: 5px;color: white;border-color: white">Delete</button>
    </div>
    <div class="container boat_profile px-4 px-lg-5 my-5">
      <div class="row align-items-center">
        <div class="col-md-6">

          <carousel :per-page="1" :navigationEnabled="true" :mouse-drag="false" :autoplay="true" :adjustable-height="true">
            <slide  v-for="i in boat.images">
              <img class="d-block w-100" :src="require('../assets/images/'+i.path)" alt="First slide" style="border-radius: 2%">
            </slide>
          </carousel>

          <div class="row thumbs pt-3 ">
            <span v-for="i in boat.images" class="side_photo col-3 px-1" style="padding-top: 10px;"><img :src="require('../assets/images/'+i.path)" alt="Boat photo" class="img-responsive" width="130px" height="130px"></span>
          </div>
        </div>
        <div class="col-md-6 pt-5" >
          <div class="row m-2">
            <h1 class="col-9 " >{{boat.name}}</h1>
            <div class="col-3 pt-3">Grade 5 <font-awesome-icon icon="fa-solid fa-star" style="color: gold"/></div>
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
            <div class="col-6">
              <p style="font-size: 25px">Additional services</p>
              <div class="services">
                <p v-for="as in boat.additionalServices"><font-awesome-icon class="small-icon" icon="fa-solid fa-check-circle" /> {{as.name}}</p>
              </div>
            </div>
            <div class="col-6">
              <p style="font-size: 25px">Navigation equipment</p>
              <div>
                <p v-for="ne in boat.navigationEquipment"><font-awesome-icon class="small-icon" icon="fa-solid fa-check-circle" /> {{ne.equipment}}</p>
              </div>
            </div>

          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-4 p-3" style="background:aliceblue;border-radius: 5%">
          <p style="font-size: 25px;">Fishing equipment</p>
          <div class="rules">
            <p v-for="fe in boat.fishingEquipment"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{fe.equipment}}</p>
          </div>
        </div>
        <div class="col-8" style="padding-left: 15px;">
          <div class="px-3" style="background: aliceblue;border-radius: 2%;">
            <p class="pt-3" style="font-size: 25px;">Reservation</p>
            <div class="pl-3 row">
              <div class="col-5">
                Start date:
                <input type="date" name="startDate" placeholder="dd-mm-yyyy">
              </div>
              <div class="col-5">
                End date:
                <input type="date" name="endDate" placeholder="dd-mm-yyyy">
              </div>
              <div class="col-4 pt-4">
                Person number:
                <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
              </div>

            </div>
            <div class="res_button"><button type="button" class="btn ">Reserve</button></div>
          </div>
        </div>
      </div>
      <div class="row py-3">
        <div class="col-4" style="background:aliceblue;border-radius: 5%">
          <p style="font-size: 25px;">Rules</p>
          <div class="rules">
            <p v-for="r in boat.rules"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.rule}}</p>
          </div>
        </div>
      </div>

      <div class="row" >
        <div class="col-12" style="background: aliceblue">
          <div class="row pt-3" style="padding-left: 10px;">
            <h3 id="quick_heading" class="col-10">Quick reservations - enormous discounts!</h3>
            <span class="col-2" style="float: right;">
            <button type="button" v-on:click="showModal()" style="color: white;background: #c91d1d;" class="btn btn-info btn-lg ">Add new</button>
            <AddQuickReservation
              :id="boat.id"
              style="width: 300px"
              v-show="isModalVisible"
              v-on:click="closeModal()"
            />
            </span>
          </div>
          <div v-if="quick.length == 0">
            <h4 class="p-3">There are no quick reservations for now.</h4>
          </div>

          <div class="row p-3">
            <div class="col-4 p-3 m-2 quick_res zoom" v-for="q in boat.quickReservations">
              <div>
                <h4 class="res_date">{{q.startDateTime[2]+"."+q.startDateTime[1]+"."+q.startDateTime[0]+"."}} - {{q.endDateTime[2]+"."+q.endDateTime[1]+"."+q.endDateTime[0]+"."}}</h4>
                <div class="discount">{{q.discount}}%</div>
              </div>
              <p class="py-2"><font-awesome-icon icon="fa-solid fa-user-friends"/> {{q.maxPersonNum}}</p>
              $<span class="text-decoration-line-through">{{q.price}}</span>
              $<span class="before_price">{{q.discountedPrice}}</span>
              <div class="quick_res_btn"><button type="button" class="btn">RESERVE</button></div>
            </div>

          </div>

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

export default {
  name: "BoatProfile",
  components:{
    BoatOwnerNavbar,
    AddQuickReservation
  },
  data: function (){
    return{
      boat: '',
      quick:[],
      isModalVisible: false,
    }
  },
  mounted:function (){

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/boats/3", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.boat = response.data,this.quick=this.boat.quickReservations))

  },
  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully deleted!</p>`
      let text = `<p style="font-size: 20px">Successfully deleted boat!</p>`
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
          this.show('foo-css', 'success')
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
