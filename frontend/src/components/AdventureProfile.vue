<template>
  <div class="AdventureProfile">
    <AdminNavbar v-if="role === 'ROLE_admin'" :isAdmin="true"></AdminNavbar>
    <fishing-instructor-navbar  v-if="role === 'ROLE_fishingInstructor'"></fishing-instructor-navbar>
    <MainNavbar  v-if="role === null"></MainNavbar>
    <ClientNavbar v-if="role === 'ROLE_client'"></ClientNavbar>
    <section class="profile_main py-lg-3" style="background-image: url('https://images.unsplash.com/photo-1645032492550-4cf6a31c3cea?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTY5fHxncmVlbiUyMGJsdWUlMjBwaG90b3N8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60');background-size: 100% 100%; ">
      <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
        <router-link  v-if="role === 'ROLE_fishingInstructor'" class="col-1 rounded-pill" :to="{ name: 'UpdateAdventure',params:{id:adventure.id} }" style="background: #2e6b6b;border: none;color: white;margin: 5px" tag="button">Edit</router-link>
        <button v-if="role === 'ROLE_fishingInstructor'" type="button"  class="col-1 rounded-pill" v-on:click="deleteAdventure()" style="background: #2e6b6b;border: none;color:white ;margin: 5px">Delete</button>
      </div>
      <div class="container adventure_profile px-4 px-lg-5 my-5" >
        <div class="row align-items-center pt-5">
          <div class="col-md-6 p-3">

              <carousel :per-page="1"  :navigationEnabled="true" :mouse-drag="false" :autoplay="true" :adjustable-height="true">
                <slide  v-for="url in imagesUrl">
                  <img class="d-block w-100" :src="url" alt="First slide" style="border-radius: 2%;height: 450px">
                </slide>
              </carousel>

          </div>
          <div class="col-md-6"  >
            <div class="row">
              <div class="col-9 fw-bolder " style="font-size: 35px">{{adventure.name}}</div>
              <star-rating class="col-3" :rating=adventure.averageGrade :read-only="true" :increment="0.1" :star-size="25" :size="200"></star-rating>
            </div>

            <div class="fs-5 m-3">
              <span class="text-decoration-line-through">$45.00</span>
              <span>$40.00</span>
            </div>
            <p class="lead p-1">{{adventure.description}}</p>
            <div class="row number_info  p-1">
              <p class="col-2"> <font-awesome-icon icon="fa-solid fa-user-friends"/>  {{adventure.maxPersonNum}}</p>
              <p class="col-2" ><font-awesome-icon icon="fa-solid fa-percentage"/> {{adventure.cancelFee}}<span style="font-size: xx-small">(cancel fee)</span> </p>
            </div>

            <div class="row p-1">
              <p style="font-size: 25px">Additional services</p>
              <div class="row services">
                <p class="col-5" v-for="as in adventure.additionalServices"><font-awesome-icon  icon="fa-solid fa-check-circle"/> {{as.name}}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="row p-3">
          <div class="col-5" style="background: #f8f2ec;border-radius: 5%">
            <p style="font-size: 25px;">Rules</p>
            <div class="rules">
              <p v-for="r in adventure.rules"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.rule}}</p>
            </div>
          </div>

          <div class="col-5" style="background: #f8f2ec;border-radius: 5%;margin-left: 95px;padding-left: 25px">
            <p style="font-size: 25px;">Fishing equipment</p>
            <div class="rules">
              <p v-for="r in adventure.fishingEquipment"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.equipment}}</p>
            </div>
          </div>


        </div>
        <hr/>
        <div class="row col-12 p-3" style="alignment: center;">
          <div class="col-4 p-3" style="background: #f8f2ec;border-radius: 5% ">
            <h3>Fishing instructor</h3>
            <div class="row p-3">
              <div >
                <span class="side_photo "><img :src="mainImg" alt="Cottage photo4" ></span>
              </div>
              <br/>
              <div class=" p-3 m-2 ">
                <h5>Short biography</h5>
                {{fishingInstructor.biography}}
              </div>
            </div>

          </div>
          <div class="col-1" style="width: 60px"></div>
          <div class="col-7 py-4 " style="background: #f8f2ec;border-radius: 5%;width: 60% ">
            <h4><b>Location:</b>  {{address.country}}, {{address.city}}, {{address.street}}</h4>
            <l-map style="height: 300px" :zoom="zoom" :center="center">
              <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
              <l-marker :lat-lng="markerLatLng"></l-marker>
            </l-map>
          </div>
        </div>
        <br/>

        <hr/>
        <div class="row mr-3 mb-2" >
          <div class="col-12 mx-3" style="background: #f8f2ec;">
            <div class="row pt-3" style="padding-left: 10px">
              <h3 id="quick_heading" class="col-10">Quick reservations - enormous discounts!</h3>
              <span class="col-2" style="float: right;background: #f8f2ec;">
            <button v-if="role === 'ROLE_fishingInstructor'" type="button" v-on:click="showModal()" style="color: white;background: #c91d1d;" class="btn btn-info btn-lg ">Add new</button>
            <AddQuickReservation
              :id="adventure.id"
              style="width: 300px"
              v-show="isModalVisible"
              v-on:click="closeModal()"
            />
            </span>
            </div>

            <div class="row p-3">
              <div v-if="quick.length === 0">
                <h4 class="p-3">There are no quick reservations for now.</h4>
              </div>
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


      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import AddQuickReservation from "./AddQuickReservation";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";
import MainNavbar from "./header/MainNavbar";
import ClientNavbar from "./header/ClientNavbar";
import AdminNavbar from "./header/AdminNavbar";
import router from "../router";

export default {
  name: "AdventureProfile",
  components: {
    ClientNavbar,
    AdminNavbar,
    MainNavbar,
    FishingInstructorNavbar,
    AddQuickReservation,
  },
  data: function(){
    return{
      adventure: '',
      mainPhoto:'',
      fishingInstructor:'',
      server: "https://rental-app-6.herokuapp.com",
      isModalVisible: false,
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution: '',
      zoom: 15,
      center: [0,0],
      markerLatLng: [0, 0],
      address:'',
      adventureId: this.$route.params.id,
      config:'',
      role:'',
      mainImg:'',
      imagesUrl:[],
      quick:[]
    }
  },
  mounted:function (){
    this.role = sessionStorage.getItem("role");
    window.scrollTo(0, 0)
    axios
      .get("https://rental-app-6.herokuapp.com"+"/api/adventures/"+this.adventureId)
      .then(response => {
        this.adventure = response.data
        this.fishingInstructor = this.adventure.fishingInstructor
        this.address=response.data.address
        response.data.images.forEach(image => {
          this.loadImage(image.path);
        });
        this.loadOnlyOneImage(this.adventure.fishingInstructor.mainPhoto);
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


  },
  methods:{
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },
    showModal:function() {
      this.isModalVisible = true;
    },
    closeModal:function() {
      this.isModalVisible = false;
    },
    deleteAdventure:function (){
      let id = this.adventure.id
      axios.delete("https://rental-app-6.herokuapp.com"+"/api/adventures/deleteAdventure/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully deleted!</p>`,`<p style="font-size: 20px">Successfully deleted adventure!</p>`)
          setTimeout(() => { }, 3000)
          router.push('/adventures');
        }).catch((error) => {
        this.show('foo-css', 'error',`<p style="font-size: 25px">Deletion is not possible!</p>`,`<p style="font-size: 20px">Adventure has reservations.</p>`)
      });


    },
    loadImage(name) {
      axios.get("https://rental-app-6.herokuapp.com"+"/api/images/getImage/"+name,{responseType:"blob"})
        .then(response => {
          this.imagesUrl.push(URL.createObjectURL(response.data));
        })
        .catch((error) =>{
          console.log(error);
        });
    },
    loadOnlyOneImage(name) {
      axios.get("https://rental-app-6.herokuapp.com"+"/api/images/getImage/"+name,{responseType:"blob"})
        .then(response => {
          this.mainImg=URL.createObjectURL(response.data);
        })
        .catch((error) =>{
          console.log(error);
        });
    },
    reserve:function(id) {
      axios.put("https://rental-app-6.herokuapp.com"+"/api/reservation/makeReservationFromQuick/"+id, {},{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully reserved!</p>`,`<p style="font-size: 20px">Successfully reserved quick reservation.</p>`)
          setTimeout(() => { location.reload(); }, 2000)
        }).catch(error => {
        if(!error.response || error.response.status === 403){
          this.show('foo-css', 'error',`<p style="font-size: 25px">Forbidden Error!</p>`,`<p style="font-size: 20px">You can not make quick reservation.</p>`)
        }else if(error.response.status === 409){
          this.show('foo-css', 'error',`<p style="font-size: 25px">Already reserved</p>`,`<p style="font-size: 20px">Someone else has just reserved the action.</p>`)
        } else if(error.response.status === 400){
          this.show('foo-css', 'error',`<p style="font-size: 25px">Already reserved</p>`,`<p style="font-size: 20px">You already have a reservation in this period.</p>`)
        }else if(error.response.status === 401){
          this.show('foo-css', 'error',`<p style="font-size: 25px">You can't reserve</p>`,`<p style="font-size: 20px">Log in to be able to make a reservation.</p>`)
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

#quick_adventure_heading{
  font-size: 25px;
  color:#2e6b6b;
  font-weight: bold;
}

.adventure_discount{
  background: #2e6b6b;
  display: inline-block;
  margin-left: 15%;
  color: white;
  border-radius: 50%;
  padding: 5px
}

.quick_adventure_res{
  border-radius: 5%;
  background: white;
  font-size: 20px;
  border: 3px solid #2e6b6b;
  width: 32%;
}


.quick_res_adventure_btn{
  margin-left: 70%;
}

.quick_res_adventure_btn button{
  background: #2e6b6b;
  color: white;
}
</style>
