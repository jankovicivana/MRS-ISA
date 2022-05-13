<template>
  <div class="AdventureProfile">
    <fishing-instructor-navbar></fishing-instructor-navbar>
    <section class="profile_main py-lg-3" style="background-image: url('https://images.unsplash.com/photo-1645032492550-4cf6a31c3cea?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTY5fHxncmVlbiUyMGJsdWUlMjBwaG90b3N8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60');background-size: 100% 100%; ">
      <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
        <router-link class="col-1 rounded-pill" :to="{ name: 'UpdateAdventure',id:adventure.id }" style="background: #2e6b6b;border: none;color: white;margin: 5px" tag="button">Edit</router-link>
        <button type="button" class="col-1 rounded-pill" v-on:click="deleteAdventure()" style="background: #2e6b6b;border: none;color:white ;margin: 5px">Delete</button>
      </div>
      <div class="container adventure_profile px-4 px-lg-5 my-5" >
        <div class="row align-items-center pt-5">
          <div class="col-md-6">

              <carousel :per-page="1"  :navigationEnabled="true" :mouse-drag="false" :autoplay="true" :adjustable-height="true">
                <slide  v-for="i in adventure.images">
                  <img class="d-block w-100" :src="require('../assets/images/'+i.path)" alt="First slide" style="border-radius: 2%;height: 450px">
                </slide>

              </carousel>


          </div>
          <div class="col-md-6"  >
            <div class="row">
              <div class="col-9 fw-bolder " style="font-size: 35px">{{adventure.name}}</div>
              <div class="col-3 ">Grade 5 <font-awesome-icon icon="fa-solid fa-star"/></div>
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
              <div class="services">
                <p v-for="as in adventure.additionalServices"><font-awesome-icon  icon="fa-solid fa-check-circle"/> {{as.name}}</p>
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
        <div class="row col-12" style="alignment: center;">
          <div class="px-3" style="background: #f8f2ec; ">
            <h3>Fishing instructor</h3>
            <div class="row p-3">
              <div class="col-6  ">
                <span class="side_photo p-lg-3 px-1"><img :src="require('../assets/images/'+fishingInstructor.mainPhoto)" alt="Cottage photo4" width="330px"                       ></span>
              </div>

              <br/>

              <div class=" p-3 m-2 ">
                <h5>Short biography</h5>

                {{fishingInstructor.biography}}
              </div>



            </div>

          </div>
        </div>
        <br/>
        <hr/>
        <div class="row col-12" style="alignment: center;">
          <div class="px-3" style="background: #f8f2ec;">
            <p style="font-size: 25px;">Reservations</p>
            <div class="pl-6" style="height: 90px" >
              <span>
              Start date:
              <input type="date" name="startDate" placeholder="dd-mm-yyyy">
              </span>
              <span style="margin-left: 35px">
              End date:
              <input type="date" name="endDate" placeholder="dd-mm-yyyy">
              </span>
              <span style="margin-left: 35px">
              Person number:
              <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
              </span>
              <span style="margin-left: 35px;margin-bottom: 35px">
                <button type="button" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF" class="btn ">Reserve</button>
              </span>
            </div>

          </div>

        </div>
        <hr/>
        <div class="row " >
          <div class="col-12 mx-3" style="background: #f8f2ec;">
            <div class="row pt-3" style="padding-left: 10px">
              <h3 id="quick_heading" class="col-10">Quick reservations - enormous discounts!</h3>
              <span class="col-2" style="float: right;background: #f8f2ec;">
            <button type="button" v-on:click="showModal()" style="color: white;background: #c91d1d;" class="btn btn-info btn-lg ">Add new</button>
            <AddQuickReservation
              :id="adventure.id"
              style="width: 300px"
              v-show="isModalVisible"
              v-on:click="closeModal()"
            />
            </span>
            </div>

            <div class="row p-3">
              <div class="col-4 p-3 m-2 quick_res zoom" v-for="q in adventure.quickReservations">
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
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "AdventureProfile",
  components: {
    FishingInstructorNavbar,
    AddQuickReservation,
  },
  data: function(){
    return{
      adventure: '',
      mainPhoto:'',
      fishingInstructor:'',
      server: process.env.VUE_APP_SERVER_PORT,
      isModalVisible: false,
    }
  },
  mounted:function (){

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/2")
      .then(response => (
        this.adventure = response.data,this.fishingInstructor = this.adventure.fishingInstructor
      ))


  },
  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully deleted!</p>`
      let text = `<p style="font-size: 20px">Successfully deleted adventure!</p>`
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
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/adventures/deleteAdventure/"+id)
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
